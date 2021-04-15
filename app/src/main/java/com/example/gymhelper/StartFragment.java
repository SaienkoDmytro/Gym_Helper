package com.example.gymhelper;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.gymhelper.databinding.FragmentStartBinding;

import org.jetbrains.annotations.NotNull;

public class StartFragment extends Fragment {

    private FragmentStartBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentStartBinding.inflate(inflater, container, false);
        ((StartActivity)getActivity()).hideHome();
        return binding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        NavController navController = Navigation.findNavController(view);


        binding.buttonStartTimer.setOnClickListener(v -> navController.navigate(R.id.action_startFragment_to_timerFragment));

        binding.buttonWorkoutProjectActivity.setOnClickListener(v -> navController.navigate(R.id.action_startFragment_to_mainActivity));

        binding.buttonAccountFragment.setOnClickListener(v -> navController.navigate(R.id.action_startFragment_to_accountFragment));

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}