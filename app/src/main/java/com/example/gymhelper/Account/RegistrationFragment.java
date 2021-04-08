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

import com.example.gymhelper.R;
import com.example.gymhelper.ViewModel.FragmentViewModel;
import com.example.gymhelper.databinding.FragmentTimerBinding;
import com.example.gymhelper.databinding.RegistrationFragmentBinding;

public class RegistrationFragment extends Fragment {

    private FragmentViewModel mViewModel;
    private RegistrationFragmentBinding binding;
    private View view;

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
        mViewModel = new ViewModelProvider(this).get(FragmentViewModel.class);
        mViewModel.getAccount().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                binding.editTextAccountName.setText(s);
            }
        });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        NavController navController = Navigation.findNavController(view);
        binding.buttonRegistrationAccount.setOnClickListener(v -> navController.navigate(R.id.action_registrationFragment_to_changeAccountFragment));
        binding.buttonLoginAccount.setOnClickListener(v -> navController.navigate(R.id.action_registrationFragment_to_accountFragment));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}