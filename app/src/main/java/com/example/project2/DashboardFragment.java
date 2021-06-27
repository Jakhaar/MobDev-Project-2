package com.example.project2;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.Date;

public class DashboardFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);
        Spinner timePeriodSpinner = view.findViewById(R.id.timePeriodSpinner);

        String trackDescription = "Zeit - 00:00, Ø Tempo - 00\" 00'";
        String title[] = {"LAUF", "SPAZIERGANG"};
        int icons[] = {R.drawable.ic_directions_run_black_24dp, R.drawable.ic_directions_walk_black_24dp};

        ArrayList<String> timePeriod = new ArrayList<>(3);
        ArrayAdapter<String> timePeriodAdapter = new ArrayAdapter<>(getContext(),R.layout.style_spinner, timePeriod);

        //Adding Values to the List
        timePeriod.add("Woche");
        timePeriod.add("Monat");
        timePeriod.add("Jahr");
        timePeriod.add("Alle");
        timePeriodSpinner.setAdapter(timePeriodAdapter);

        return view;
    }


}
