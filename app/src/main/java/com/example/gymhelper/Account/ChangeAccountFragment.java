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
import com.example.gymhelper.ViewModel.FragmentViewModel;
import com.example.gymhelper.databinding.ChangeAccountFragmentBinding;
import com.example.gymhelper.databinding.FragmentTimerBinding;

public class ChangeAccountFragment extends Fragment {

    private FragmentViewModel mViewModel;
    private ChangeAccountFragmentBinding binding;
    private View view;

    public static ChangeAccountFragment newInstance() {
        return new ChangeAccountFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = ChangeAccountFragmentBinding.inflate(inflater, container, false);
        view = binding.getRoot();
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(FragmentViewModel.class);
        // TODO: Use the ViewModel
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        NavController navController = Navigation.findNavController(view);
        binding.buttonRegistrationAccount.setOnClickListener(v -> navController.navigate(R.id.action_changeAccountFragment_to_registrationFragment));
        binding.buttonChangeAccount.setOnClickListener(v -> navController.navigate(R.id.action_changeAccountFragment_to_registrationFragment));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}