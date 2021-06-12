package com.example.project2.ui.spazieren;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SpazierenViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public SpazierenViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is home fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}