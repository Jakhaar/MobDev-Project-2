package com.example.project2.ui.laufen;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class LaufenViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public LaufenViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is LAUFEN");
    }

    public LiveData<String> getText() {
        return mText;
    }
}