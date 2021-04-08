package com.example.gymhelper.ViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class FragmentViewModel extends ViewModel {

    private MutableLiveData<String> mText;


    public FragmentViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Account");
    }

    public LiveData<String> getAccount() {
        return mText;
    }
}