package com.example.gymhelper.ViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class FragmentViewModel extends ViewModel {

    private final MutableLiveData<String> email = new MutableLiveData<>();
    private final MutableLiveData<String> password = new MutableLiveData<>();
    private final MutableLiveData<String> account = new MutableLiveData<>();


    public LiveData<String> getEmail() {
        return email;
    }

    public LiveData<String> getAccount() {
        return account;
    }

    public void setEmail(String mail) {
        email.setValue(mail);
    }

    public void setAccount(String acc) {
        account.setValue(acc);
    }



}