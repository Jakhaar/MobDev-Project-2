package com.example.project2.ui.spazieren;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.project2.R;

public class SpazierenFragment extends Fragment {

    private SpazierenViewModel spazierenViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        spazierenViewModel =
                new ViewModelProvider(this).get(SpazierenViewModel.class);
        View root = inflater.inflate(R.layout.fragment_spazieren, container, false);

        return root;
    }
}