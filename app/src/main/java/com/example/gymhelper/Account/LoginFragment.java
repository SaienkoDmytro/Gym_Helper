package com.example.gymhelper.Account;

import androidx.lifecycle.ViewModelProvider;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.gymhelper.R;
import com.example.gymhelper.StartActivity;
import com.example.gymhelper.ViewModel.FragmentViewModel;
import com.example.gymhelper.databinding.LoginFragmentBinding;
import com.google.firebase.auth.FirebaseAuth;

import static android.view.View.VISIBLE;

public class LoginFragment extends Fragment {

    private FragmentViewModel mViewModel;
    private LoginFragmentBinding binding;
    private View alertForget;
    private View view;
    private FirebaseAuth firebaseAuth;
    private String account, password;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = LoginFragmentBinding.inflate(inflater, container, false);
        alertForget = inflater.inflate(R.layout.forget_pass_dialog, container, false);
        view = binding.getRoot();
        firebaseAuth = FirebaseAuth.getInstance();
        ((StartActivity)getActivity()).hideHome();
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(getActivity()).get(FragmentViewModel.class);
        mViewModel.getEmail().observe(getViewLifecycleOwner(), s -> binding.editTextEmail.setText(s));
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        NavController navController = Navigation.findNavController(view);
        EditText email = alertForget.findViewById(R.id.forget_pass_enter_email);

        if (firebaseAuth.getCurrentUser() != null) {
            navController.navigate(R.id.action_loginFragment_to_startFragment);
        }

        binding.buttonContinueWithoutRegistration.setOnClickListener(v -> new AlertDialog.Builder(getActivity())
                .setTitle(R.string.alert_dialog_title_login)
                .setMessage(R.string.alert_dialog_mess_login)
                .setCancelable(false)
                .setPositiveButton(R.string.alert_dialog_login_ok, (dialog, which) -> navController.navigate(R.id.action_loginFragment_to_startFragment))
                .setNegativeButton(R.string.alert_dialog_login_cancel, null)
                .show());

        binding.buttonRegistrationAccount.setOnClickListener(v -> {
            mViewModel.setEmail(binding.editTextEmail.getText().toString());
            navController.navigate(R.id.action_loginFragment_to_registrationFragment);
        });
        binding.buttonLoginAccount.setOnClickListener(v -> {
            account = binding.editTextEmail.getText().toString().trim();
            password = binding.editTextAccountPassword.getText().toString().trim();
            if (checkInput(account, password)) {
                binding.progressBarLogin.setVisibility(VISIBLE);
                firebaseAuth.signInWithEmailAndPassword(account, password).addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(getActivity(), R.string.log_in_successful, Toast.LENGTH_SHORT).show();
                        mViewModel.setEmail(binding.editTextEmail.getText().toString());
                        navController.navigate(R.id.action_loginFragment_to_startFragment);
                    } else {
                        Toast.makeText(getActivity(), "" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        binding.progressBarLogin.setVisibility(View.GONE);
                    }
                });
            }
        });


        binding.buttonForgotPassword.setOnClickListener(v -> {
            if (alertForget.getParent() != null) {
                ((ViewGroup)alertForget.getParent()).removeView(alertForget);
            }
            new AlertDialog.Builder(getActivity())
                    .setTitle("Reset your password")
                    .setMessage("Please enter your registration Email")
                    .setCancelable(false)
                    .setPositiveButton("Reset", (dialog, which) -> {
                        if (email.getText().toString().isEmpty()) {
                            email.setError("Please enter your Email");
                            return;
                        }
                        firebaseAuth.sendPasswordResetEmail(email.getText().toString()).addOnSuccessListener(aVoid -> Toast.makeText(getActivity(), "Reset Email sent", Toast.LENGTH_SHORT).show())
                                .addOnFailureListener(e -> Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show());
                    }).setNegativeButton("Cancel", null)
                    .setView(alertForget)
                    .show();
        });


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
        view = null;
        alertForget = null;
    }

    private boolean checkInput(String acc, String pass) {
        boolean result = true;
        if (TextUtils.isEmpty(acc)) {
            binding.editTextEmail.setError(getString(R.string.empty_email_field));
            result = false;
        }
        if (TextUtils.isEmpty(pass)) {
            binding.editTextAccountPassword.setError(getString(R.string.empty_password_field));
            result = false;
        }
        return result;
    }

}