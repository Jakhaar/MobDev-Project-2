package com.example.project2.ui.laufen;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.project2.R;
import com.example.project2.ui.spazieren.SpazierenViewModel;

public class LaufenFragment extends Fragment {

    private LaufenViewModel laufenViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        laufenViewModel =
                new ViewModelProvider(this).get(LaufenViewModel.class);
        View root = inflater.inflate(R.layout.fragment_laufen, container, false);

        return root;
    }

}