package com.example.gymhelper.Account;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.gymhelper.R;
import com.example.gymhelper.StartActivity;
import com.example.gymhelper.ViewModel.FragmentViewModel;
import com.example.gymhelper.databinding.RegistrationFragmentBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import static android.content.ContentValues.TAG;
import static android.view.View.VISIBLE;

public class RegistrationFragment extends Fragment {

    private FragmentViewModel mViewModel;
    private RegistrationFragmentBinding binding;
    private View view;
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firebaseFirestore;
    private String account, email, password, passwordConfirm, userID;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = RegistrationFragmentBinding.inflate(inflater, container, false);
        view = binding.getRoot();
        ((StartActivity)getActivity()).showHome();
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(getActivity()).get(FragmentViewModel.class);
        mViewModel.getEmail().observe(getViewLifecycleOwner(), s -> binding.editTextEmail.setText(s));
        mViewModel.getAccount().observe(getViewLifecycleOwner(), s -> binding.editTextAccountName.setText(s));
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        NavController navController = Navigation.findNavController(view);
        binding.buttonRegisterAccount.setOnClickListener(v -> {
            if (firebaseAuth.getCurrentUser() == null) {
                account = binding.editTextAccountName.getText().toString();
                email = binding.editTextEmail.getText().toString().trim();
                password = binding.editTextAccountPassword.getText().toString().trim();
                passwordConfirm = binding.editTextAccountPasswordConfirm.getText().toString().trim();
                if (checkInput(account, email, password, passwordConfirm)) {
                    binding.progressBarRegister.setVisibility(VISIBLE);
                    firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            mViewModel.setAccount(account);
                            mViewModel.setEmail(email);
                            userID = firebaseAuth.getCurrentUser().getUid();
                            DocumentReference documentReference = firebaseFirestore.collection("accounts").document(userID);
                            Map<String, Object> user = new HashMap<>();
                            user.put("accName", account);
                            user.put("Email", email);
                            documentReference.set(user).addOnSuccessListener(aVoid -> Log.d(TAG, "onAddFireStore OK: " + userID)).addOnFailureListener(e -> Log.d(TAG, "onAddFireStore Failed: " + e.toString()));
                            Toast.makeText(getActivity(), R.string.account_created, Toast.LENGTH_SHORT).show();
                            navController.navigate(R.id.action_registrationFragment_to_loginFragment);
                        } else {
                            Toast.makeText(getActivity(), "" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            binding.progressBarRegister.setVisibility(View.GONE);
                        }

                    });
                }
            } else {
                navController.navigate(R.id.action_registrationFragment_to_accountFragment);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
        view = null;
    }

    private boolean checkInput(String acc, String mail, String pass, String passConf) {
        boolean result = true;
        if (TextUtils.isEmpty(acc)){
            binding.editTextAccountName.setError(getString(R.string.account_field));
            result = false;
        }
        if (TextUtils.isEmpty(mail)){
            binding.editTextEmail.setError(getString(R.string.email_field_empty));
            result = false;
        }
        if (TextUtils.isEmpty(pass)) {
            binding.editTextAccountPassword.setError(getString(R.string.password_filed_empty));
            result = false;
        }
        if (pass.length() < 6) {
            binding.editTextAccountPassword.setError(getString(R.string.password_6symbols));
            result = false;
        }
        if (!pass.equals(passConf)) {
            binding.editTextAccountPasswordConfirm.setError(getString(R.string.password_equals));
        }
        return result;
    }

}