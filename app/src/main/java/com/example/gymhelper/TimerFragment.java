package com.example.gymhelper;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;


import com.example.gymhelper.databinding.FragmentTimerBinding;

import org.jetbrains.annotations.NotNull;

import java.util.Locale;

import static android.content.Context.INPUT_METHOD_SERVICE;
import static android.content.Context.MODE_PRIVATE;


public class TimerFragment extends Fragment {


    private FragmentTimerBinding binding;


    private CountDownTimer mCountDownTimer;
    private boolean mTimerRunning;
    private long mStartTimeInMillis;
    private long mTimeLeftInMillis;
    private long mEndTime;
    private View view;


    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentTimerBinding.inflate(inflater, container, false);
        view = binding.getRoot();
        ((StartActivity)getActivity()).showHome();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        NavController navController = Navigation.findNavController(view);

        binding.buttonWorkoutProjectActivity.setOnClickListener(v -> navController.navigate(R.id.action_timerFragment_to_mainActivity));

        binding.buttonSet.setOnClickListener(v -> {
            String input = binding.editTextInput.getText().toString();
            if (input.length() == 0) {
                Toast.makeText(getActivity(), R.string.field_empty, Toast.LENGTH_SHORT).show();
                return;
            }
            long millisInput = Long.parseLong(input) * 60000;
            if (millisInput == 0) {
                Toast.makeText(getActivity(), R.string.possitive_number, Toast.LENGTH_SHORT).show();
                return;
            }
            setTime(millisInput);
            binding.editTextInput.setText("");
        });

        binding.buttonStartPause.setOnClickListener(v -> {
            if (mTimerRunning) {
                pauseTimer();
            } else {
                startTimer();
            }
        });

        binding.buttonReset.setOnClickListener(v -> resetTimer());
    }

    private void setTime(long milliseconds) {
        mStartTimeInMillis = milliseconds;
        resetTimer();
        closeKeyboard();
    }
    
    private void startTimer() {
        mEndTime = System.currentTimeMillis() + mTimeLeftInMillis;
        mCountDownTimer = new CountDownTimer(mTimeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mTimeLeftInMillis = millisUntilFinished;
                updateCountDownText();
            }
            @Override
            public void onFinish() {
                mTimerRunning = false;
                updateWatchInterface();
            }
        }.start();
        mTimerRunning = true;
        updateWatchInterface();
    }

    private void pauseTimer() {
        mCountDownTimer.cancel();
        mTimerRunning = false;
        updateWatchInterface();
    }

    private void resetTimer() {
        mTimeLeftInMillis = mStartTimeInMillis;
        updateCountDownText();
        updateWatchInterface();
    }

    private void updateCountDownText() {
        int hours = (int) (mTimeLeftInMillis / 1000) / 3600;
        int minutes = (int) ((mTimeLeftInMillis / 1000) % 3600) / 60;
        int seconds = (int) (mTimeLeftInMillis / 1000) % 60;
        String timeLeftFormatted;
        if (hours > 0) {
            timeLeftFormatted = String.format(Locale.getDefault(),
                    "%d:%02d:%02d", hours, minutes, seconds);
        } else {
            timeLeftFormatted = String.format(Locale.getDefault(),
                    "%02d:%02d", minutes, seconds);
        }
        binding.textViewCountdown.setText(timeLeftFormatted);
    }

    private void updateWatchInterface() {
        if (mTimerRunning) {
            binding.editTextInput.setVisibility(View.INVISIBLE);
            binding.buttonSet.setVisibility(View.INVISIBLE);
            binding.buttonReset.setVisibility(View.INVISIBLE);
            binding.buttonStartPause.setText(getString(R.string.pause));
        } else {
            binding.editTextInput.setVisibility(View.VISIBLE);
            binding.buttonSet.setVisibility(View.VISIBLE);
            binding.buttonStartPause.setText(getString(R.string.start));
            if (mTimeLeftInMillis < 1000) {
                binding.buttonStartPause.setVisibility(View.INVISIBLE);
            } else {
                binding.buttonStartPause.setVisibility(View.VISIBLE);
            }
            if (mTimeLeftInMillis < mStartTimeInMillis) {
                binding.buttonReset.setVisibility(View.VISIBLE);
            } else {
                binding.buttonReset.setVisibility(View.INVISIBLE);
            }
        }
    }

    private void closeKeyboard() {
        view = getActivity().getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        SharedPreferences prefs = this.getActivity().getSharedPreferences("prefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putLong("startTimeInMillis", mStartTimeInMillis);
        editor.putLong("millisLeft", mTimeLeftInMillis);
        editor.putBoolean("timerRunning", mTimerRunning);
        editor.putLong("endTime", mEndTime);
        editor.apply();
        if (mCountDownTimer != null) {
            mCountDownTimer.cancel();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        SharedPreferences prefs = this.getActivity().getSharedPreferences("prefs", MODE_PRIVATE);
        mStartTimeInMillis = prefs.getLong("startTimeInMillis", 600000);
        mTimeLeftInMillis = prefs.getLong("millisLeft", mStartTimeInMillis);
        mTimerRunning = prefs.getBoolean("timerRunning", false);
        updateCountDownText();
        updateWatchInterface();
        if (mTimerRunning) {
            mEndTime = prefs.getLong("endTime", 0);
            mTimeLeftInMillis = mEndTime - System.currentTimeMillis();
            if (mTimeLeftInMillis < 0) {
                mTimeLeftInMillis = 0;
                mTimerRunning = false;
                updateCountDownText();
                updateWatchInterface();
            } else {
                startTimer();
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
        view = null;
    }
}