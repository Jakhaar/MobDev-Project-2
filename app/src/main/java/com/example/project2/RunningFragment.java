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

import static android.content.Context.MODE_PRIVATE;

public class RunningFragment extends Fragment {

    private int startTime, timeLeftOver, endTime, defaultStartTime = 60000;
    private boolean timerIsRunning;
    private CountDownTimer countDownTimer;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_running, container, false);
        Button startButton = view.findViewById(R.id.startButton);
        Spinner runningModeSpinner = view.findViewById(R.id.runningModeSpinner);
        EditText timerTextView = view.findViewById(R.id.timerTextView);
        //TextView textViewCountDown = view.findViewById(R.id.);

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
                timerMode(timerTextView);
                //Intent intent = new Intent(getActivity(), MapsActivity.class);
                //startActivity(intent);
            }
        });

        return view;
    }

    private void minimizeKeyboard(){
        //TODO:Code here
    }

    private String timerFormatted(int value){
        int hours = (value/1000) / 3600;
        int minutes = (value/1000) % 3600 / 60;
        int seconds = (value/1000) % 60;

        String timeFormatted = String.format(Locale.getDefault(),
                "%02d:%02d:%02d", hours, minutes, seconds);

            return timeFormatted;
    }

    private void runTimer(){
        if(timerIsRunning){
            stopTimer();
        } else {
            startTimer();
        }
    }

    private void startTimer() {
        countDownTimer = new CountDownTimer(timeLeftOver, 1000) {
            @Override
            public void onTick(long l) {
                timeLeftOver = (int) l;
                Toast.makeText(getContext(), "" + timerFormatted(timeLeftOver), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFinish() {
                Toast.makeText(getContext(), "Zeit ist vorbei!", Toast.LENGTH_LONG).show();
            }
        }.start();

        timerIsRunning = true;
    }

    private void stopTimer() {
        countDownTimer.cancel();
        timerIsRunning = false;
    }

    //Saves important data when there are Runtime Config changes
    //Closing the app
    @Override
    public void onStop() {
        super.onStop();

        SharedPreferences sharedPreferences = getContext().getSharedPreferences("sharedPreferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putInt("startTime", startTime);
        editor.putInt("timeLeft", timeLeftOver);
        editor.putBoolean("timerIsRunning", timerIsRunning);
        editor.putInt("endTime",endTime);

        editor.apply();

        if(countDownTimer != null){
            countDownTimer.cancel();
        }
    }

    @Override
    public void onStart() {
        super.onStart();

        SharedPreferences sharedPreferences = getContext().getSharedPreferences("sharedPreferences", MODE_PRIVATE);
        timeLeftOver = sharedPreferences.getInt("timeLeft", startTime);
        timerIsRunning = sharedPreferences.getBoolean("timerIsRunning", false);
        startTime = sharedPreferences.getInt("startTime", defaultStartTime);


        if(timerIsRunning){
            endTime = sharedPreferences.getInt("endTime", 0);
            timeLeftOver = (int) (endTime - System.currentTimeMillis());

            if(timeLeftOver < 0){
                timeLeftOver = 0;
                timerIsRunning = false;
            }else {
                startTimer();
            }
        }
    }

    private void textViewVisibility(int a){
        switch(a){
            //case 17: visibility();break;
        }
    }

    private void visibility(EditText editText, boolean bool) {
        if(bool){
            editText.setVisibility(getView().INVISIBLE);
        } else {
            editText.setVisibility(getView().VISIBLE);
        }
    }

    private void timerMode(TextView timerTextView){
        String userInput = timerTextView.getText().toString();
        if(userInput.length() == 0){
            Toast.makeText(getContext(),"Bitte gebe ein Wert ein", Toast.LENGTH_LONG).show();
            return;
        }

        int time = Integer.parseInt(userInput) * 60000;
        if(time == 0) {
            Toast.makeText(getContext(),"Bitte gebe einen positiven Wert ein", Toast.LENGTH_LONG).show();
            return;
        }
        setTime(time);
        runTimer();
    }

    private void setTime(int milliseconds){
        startTime = milliseconds;
        Toast.makeText(getContext(), "Timer wurde gesetzt " + startTime, Toast.LENGTH_LONG).show();
    }
}
