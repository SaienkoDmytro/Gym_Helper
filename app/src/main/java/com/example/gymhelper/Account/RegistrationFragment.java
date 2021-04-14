package com.example.gymhelper.Account;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.gymhelper.R;
import com.example.gymhelper.ViewModel.FragmentViewModel;
import com.example.gymhelper.databinding.RegistrationFragmentBinding;

public class RegistrationFragment extends Fragment {

    private FragmentViewModel mViewModel;
    private RegistrationFragmentBinding binding;
    private View view;
    private String account, password;

    public static RegistrationFragment newInstance() {
        return new RegistrationFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = RegistrationFragmentBinding.inflate(inflater, container, false);
        view = binding.getRoot();
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(getActivity()).get(FragmentViewModel.class);
        mViewModel.getAccount().observe(getViewLifecycleOwner(), s -> binding.editTextAccountName.setText(s));
        mViewModel.getPassword().observe(getViewLifecycleOwner(), s -> binding.editTextAccountPassword.setText(s));
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        NavController navController = Navigation.findNavController(view);
        binding.buttonRegisterAccount.setOnClickListener(v -> {
            account = binding.editTextAccountName.getText().toString();
            password = binding.editTextAccountPassword.getText().toString();
            if (checkInput(account, password)) {
                mViewModel.setAccount(account);
                mViewModel.setPassword(password);
                navController.navigate(R.id.action_registrationFragment_to_loginFragment);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private boolean checkInput(String acc, String pass) {
        boolean result = true;
        if (acc.equals("") || acc == null) {
            Toast.makeText(getActivity(), "Field Email can't be empty", Toast.LENGTH_SHORT).show();
            result = false;
        }
        if (pass.equals("") || pass == null) {
            Toast.makeText(getActivity(), "Field Password can't be empty", Toast.LENGTH_SHORT).show();
            result = false;
        }
        if (pass.length() < 8) {
            Toast.makeText(getActivity(), "Your Password must have 8 symbols", Toast.LENGTH_SHORT).show();
            result = false;
        }
        return result;
    }

}