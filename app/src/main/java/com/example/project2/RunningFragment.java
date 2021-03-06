package com.example.project2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import static android.content.Context.MODE_PRIVATE;
import static com.example.project2.R.string.enterValue;
import static com.example.project2.R.string.positivValue;
import static com.example.project2.R.string.timerSet;

public class RunningFragment extends Fragment {

    private int startTime, time;
    private int timeLeft;
    private int endTime;
    private boolean timerIsRunning, mapReady;
    private CountDownTimer countDownTimer;
    private Timer timer;
    private TimerTask timerTask;

    private Spinner runningModeSpinner;
    private EditText timerTextView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_running, container, false);
        Button startButton = view.findViewById(R.id.stopButton);
        runningModeSpinner = view.findViewById(R.id.runningModeSpinner);
        timerTextView = view.findViewById(R.id.timerTextView);

        ArrayList<String> runningModes = new ArrayList<>(3);
        ArrayAdapter<String> spinnerModesAdapter = new ArrayAdapter<>(getContext(),R.layout.style_spinner, runningModes);

        //Adding Values to the List
        runningModes.add("STOPPUHR");
        runningModes.add("TIMER");
        runningModes.add("STRECKE (KM)");
        runningModeSpinner.setAdapter(spinnerModesAdapter);

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetTime();
                timerMode(timerTextView);
                if(mapReady){
                    Intent intent = new Intent(getActivity(), MapsActivity.class);
                    intent.putExtra(getString(R.string.timeLeftCaps), timeLeft);
                    startActivity(intent);
                }
            }
        });
        return view;
    }

    private String getCurrentMode(Spinner spinner) {
         return spinner.getSelectedItem().toString();
    }

    private void resetTime(){
        startTime = 0;
        timeLeft = 0;
        endTime = 0;
    }

    public String timerFormatted(int value){
        int hours = (value/1000) / 3600;
        int minutes = (value/1000) % 3600 / 60;
        int seconds = (value/1000) % 60;

        return String.format(Locale.getDefault(),
                "%02d:%02d:%02d", hours, minutes, seconds);
    }

    private void runTimer(){
        if(timerIsRunning){
            switch (getCurrentMode(runningModeSpinner)){
                case "TIMER":
                    countDownStopTimer();break;
                case "STOPPUHR":
                    countUpStopTimer();break;
            }
        } else {
            switch (getCurrentMode(runningModeSpinner)){
                case "TIMER":
                    countDownStartTimer();break;
                case "STOPPUHR":
                    countUpStartTimer();break;
            }
        }
    }

    private void countUpStartTimer() {
        timerTask  = new TimerTask() {
            @Override
            public void run() {
                timeLeft++;
                Toast.makeText(getContext(),""+timerFormatted(timeLeft),Toast.LENGTH_LONG).show();
            }
        };
        timer.scheduleAtFixedRate(timerTask, 0, 1000);
    }

    private void countUpStopTimer() {
        timerTask.cancel();
        timerIsRunning = false;
    }

    private void countDownStartTimer() {
        countDownTimer = new CountDownTimer(timeLeft, 1000) {
            @Override
            public void onTick(long l) {
                timeLeft = (int) l;
            }

            @Override
            public void onFinish() {
                Toast.makeText(getContext(), R.string.timeOver, Toast.LENGTH_LONG).show();

            }
        }.start();

        timerIsRunning = true;
    }

    private void countDownStopTimer() {
        countDownTimer.cancel();
        timerIsRunning = false;
    }

    //Saves important data when there are Runtime Config changes
    //Closing the app
    @Override
    public void onStop() {
        super.onStop();

        SharedPreferences sharedPreferences = getContext().getSharedPreferences(getString(R.string.sharedPreferences), MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putInt(getString(R.string.startTime), startTime);
        editor.putInt(getString(R.string.timeLeft), timeLeft);
        editor.putBoolean(getString(R.string.timerIsRunning), timerIsRunning);
        editor.putInt(getString(R.string.endTime),endTime);

        editor.apply();

        if(countDownTimer != null){
            countDownTimer.cancel();
        }
    }

    @Override
    public void onStart() {
        super.onStart();

        SharedPreferences sharedPreferences = getContext().getSharedPreferences(getString(R.string.sharedPreferences), MODE_PRIVATE);
        timeLeft = sharedPreferences.getInt(getString(R.string.timeLeft), startTime);
        timerIsRunning = sharedPreferences.getBoolean(getString(R.string.timerIsRunning), false);
        int defaultStartTime = 60000;
        startTime = sharedPreferences.getInt(getString(R.string.startTime), defaultStartTime);


        if(timerIsRunning){
            endTime = sharedPreferences.getInt(getString(R.string.endTime), 0);
            timeLeft = (int) (endTime - System.currentTimeMillis());

            if(timeLeft < 0){
                timeLeft = 0;
                timerIsRunning = false;
            }else {
                countDownStartTimer();
            }
        }
    }

    private void timerMode(TextView timerTextView){
        String userInput = timerTextView.getText().toString();
        mapReady = false;

        switch (getCurrentMode(runningModeSpinner)){
            case "TIMER":
                if(!inputCheck(userInput)){
                    return;
                }
                setTime(time);
                runTimer();break;
            case "STOPPUHR":
                countUpStartTimer();break;
        }
        mapReady = true;
    }

    private boolean inputCheck(String userInput){
        if(userInput.length() == 0){
            Toast.makeText(getContext(), enterValue, Toast.LENGTH_LONG).show();
            return false;
        }

        time = Integer.parseInt(userInput) * 60000;
        if(time == 0) {
            Toast.makeText(getContext(), positivValue, Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    private void setTime(int milliseconds){
        startTime = milliseconds;
        timeLeft = startTime;
        Toast.makeText(getContext(), timerSet, Toast.LENGTH_LONG).show();
    }
}
