package com.example.gymhelper.ViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class FragmentViewModel extends ViewModel {

    private final MutableLiveData<String> account = new MutableLiveData<>();
    private final MutableLiveData<String> password = new MutableLiveData<>();


    public LiveData<String> getAccount() {
        return account;
    }

    public LiveData<String> getPassword() {
        return password;
    }

    public void setAccount(String acc) {
        account.setValue(acc);
    }

    public void setPassword(String pass) {
        password.setValue(pass);
    }


}