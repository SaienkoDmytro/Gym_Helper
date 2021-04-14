package com.example.gymhelper.Account;

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

import com.example.gymhelper.R;
import com.example.gymhelper.StartActivity;
import com.example.gymhelper.ViewModel.FragmentViewModel;
import com.example.gymhelper.databinding.LoginFragmentBinding;
import com.example.gymhelper.databinding.RegistrationFragmentBinding;

public class LoginFragment extends Fragment {

    private FragmentViewModel mViewModel;
    private LoginFragmentBinding binding;
    private View view;

    public static LoginFragment newInstance() {
        return new LoginFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = LoginFragmentBinding.inflate(inflater, container, false);
        view = binding.getRoot();
        ((StartActivity)getActivity()).showHome();
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
        binding.buttonRegistrationAccount.setOnClickListener(v -> {
            mViewModel.setAccount(binding.editTextAccountName.getText().toString());
            mViewModel.setPassword(binding.editTextAccountPassword.getText().toString());
            navController.navigate(R.id.action_loginFragment_to_registrationFragment);
        });
        binding.buttonLoginAccount.setOnClickListener(v -> {
            mViewModel.setAccount(binding.editTextAccountName.getText().toString());
            mViewModel.setPassword(binding.editTextAccountPassword.getText().toString());
            navController.navigate(R.id.action_loginFragment_to_accountFragment);
    });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}