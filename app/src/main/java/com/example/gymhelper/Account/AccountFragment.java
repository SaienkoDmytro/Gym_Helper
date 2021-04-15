package com.example.gymhelper.Account;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.gymhelper.R;
import com.example.gymhelper.StartActivity;
import com.example.gymhelper.ViewModel.FragmentViewModel;
import com.example.gymhelper.databinding.AccountFragmentBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import org.jetbrains.annotations.NotNull;

public class AccountFragment extends Fragment {

    private FragmentViewModel mViewModel;
    private AccountFragmentBinding binding;
    private View view;
    private View alertReset;
    private View alertEmailChange;
    private NavController navController;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser user;
    private FirebaseFirestore firebaseFirestore;
    private String userID, account, email;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = AccountFragmentBinding.inflate(inflater, container, false);
        view = binding.getRoot();
        ((StartActivity)getActivity()).showHome();
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        alertReset = inflater.inflate(R.layout.reset_pass_dialog, container, false);
        alertEmailChange = inflater.inflate(R.layout.change_email_alert, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (firebaseAuth.getCurrentUser() != null) {
            setHasOptionsMenu(true);
            user = firebaseAuth.getCurrentUser();
            userID = firebaseAuth.getCurrentUser().getUid();
            DocumentReference documentReference = firebaseFirestore.collection("accounts").document(userID);
            documentReference.addSnapshotListener(getActivity(), (value, error) -> {
                if (value != null && value.exists()) {
                    account = value.getString("accName");
                    email = value.getString("Email");
                    binding.accountName.setText(account);
                    binding.email.setText(email);
                }
            });
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        if (firebaseAuth.getCurrentUser() != null) {
            if (!firebaseAuth.getCurrentUser().isEmailVerified()){
                binding.buttonVerify.setVisibility(View.VISIBLE);
            }
        }

        binding.buttonVerify.setOnClickListener(v -> firebaseAuth.getCurrentUser().sendEmailVerification().addOnSuccessListener(aVoid -> {
            Toast.makeText(getActivity(), R.string.verification_email, Toast.LENGTH_SHORT).show();
            binding.buttonVerify.setVisibility(View.GONE);
        }));
        binding.buttonGotoMainActivity.setOnClickListener(v -> navController.navigate(R.id.action_accountFragment_to_startFragment));
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.drawer_view, menu);
    }



    @Override
    public boolean onOptionsItemSelected(@NotNull MenuItem item) {
        if (item.getItemId() == R.id.nav_change_email) {
            EditText emailChange = alertEmailChange.findViewById(R.id.edit_text_email_change);
            if (alertEmailChange.getParent() != null) {
                ((ViewGroup)alertEmailChange.getParent()).removeView(alertEmailChange);
            }
            new AlertDialog.Builder(getActivity())
                    .setTitle("Change your Email")
                    .setCancelable(false)
                    .setPositiveButton("Change", (dialog, which) -> {
                        if (emailChange.getText().toString().isEmpty()) {
                            emailChange.setError("Required filed");
                            return;
                        }
                        user.updateEmail(emailChange.getText().toString())
                                .addOnSuccessListener(aVoid -> Toast.makeText(getActivity(), "Email updated!", Toast.LENGTH_SHORT).show())
                                .addOnFailureListener(e -> Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show());

                    }).setNegativeButton("Cancel", null)
                    .setView(alertEmailChange)
                    .show();
        }
        if (item.getItemId() == R.id.nav_change_password) {
            EditText passReset = alertReset.findViewById(R.id.edit_text_reset_password);
            EditText passResetConfirm = alertReset.findViewById(R.id.edit_text_reset_password_confirm);
            if (alertReset.getParent() != null) {
                ((ViewGroup)alertReset.getParent()).removeView(alertReset);
            }
                    new AlertDialog.Builder(getActivity())
                            .setTitle("Change your password")
                            .setCancelable(false)
                            .setPositiveButton("Change", (dialog, which) -> {
                                if (passReset.getText().toString().isEmpty()) {
                                    passReset.setError("Required filed");
                                    return;
                                }
                                if (passResetConfirm.getText().toString().isEmpty()) {
                                    passResetConfirm.setError("Required filed");
                                    return;
                                }
                                if (!passReset.getText().toString().equals(passResetConfirm.getText().toString())) {
                                    passResetConfirm.setError("Password not equals!");
                                    return;
                                }
                                firebaseAuth.getCurrentUser().updatePassword(passResetConfirm.getText().toString())
                                        .addOnSuccessListener(aVoid -> {
                                    Toast.makeText(getActivity(), "Password changed!", Toast.LENGTH_SHORT).show();
                                    firebaseAuth.signOut();
                                    navController.navigate(R.id.action_accountFragment_to_loginFragment);
                                })
                                        .addOnFailureListener(e -> Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show());
                            }).setNegativeButton("Cancel", null)
                            .setView(alertReset)
                            .show();
                }
        if (item.getItemId() == R.id.nav_delete_account) {
            new AlertDialog.Builder(getActivity())
                    .setTitle("Delete your account?")
                    .setMessage("Account will be deleted with all your progress! Are you sure?")
                    .setCancelable(false)
                    .setPositiveButton("Yes, delete it!", (dialog, which) -> user.delete()
                            .addOnSuccessListener(aVoid -> {
                                Toast.makeText(getActivity(), "Account deleted!", Toast.LENGTH_SHORT).show();
                                firebaseAuth.signOut();
                                navController.navigate(R.id.action_accountFragment_to_loginFragment);
                            })
                            .addOnFailureListener(e -> Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show()))
                    .setNegativeButton("Cancel", null)
                    .show();
        }
        if (item.getItemId() == R.id.nav_exit) {
            firebaseAuth.signOut();
            navController.navigate(R.id.action_accountFragment_to_loginFragment);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
        view = null;
        alertReset = null;
        alertEmailChange = null;
    }

}