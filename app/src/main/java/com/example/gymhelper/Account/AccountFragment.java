package com.example.gymhelper.Account;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toolbar;

import com.example.gymhelper.R;
import com.example.gymhelper.StartActivity;
import com.example.gymhelper.ViewModel.FragmentViewModel;
import com.example.gymhelper.databinding.AccountFragmentBinding;
import com.example.gymhelper.databinding.FragmentTimerBinding;
import com.google.android.material.navigation.NavigationView;

import org.jetbrains.annotations.NotNull;

public class AccountFragment extends Fragment {

    private FragmentViewModel mViewModel;
    private AccountFragmentBinding binding;
    private View view;
    private NavController navController;
    private NavigationView navView;
    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private AppBarConfiguration appBarConfiguration;

    public static AccountFragment newInstance() {
        return new AccountFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }



    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = AccountFragmentBinding.inflate(inflater, container, false);
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
        navController = Navigation.findNavController(view);
        navView = binding.nvView;
        appBarConfiguration = new AppBarConfiguration.Builder(R.id.nav_settings, R.id.nav_exit).setOpenableLayout(binding.drawerLayout).build();
        NavigationUI.setupWithNavController(navView, navController);
        navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
            @Override
            public void onDestinationChanged(@NonNull NavController controller, @NonNull NavDestination destination, @Nullable Bundle arguments) {
                if (destination.getId() == R.id.nav_settings ) {
                    Navigation.findNavController(view).navigate(R.id.action_accountFragment_to_changeAccountFragment);
                }
                if (destination.getId() == R.id.nav_exit) {
                    navController.navigate(R.id.action_accountFragment_to_startFragment);
                }
            }
        });

    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.drawer_view, menu);
    }



    @Override
    public boolean onOptionsItemSelected(@NotNull MenuItem item) {
        if (item.getItemId() == R.id.nav_settings ) {
            Navigation.findNavController(view).navigate(R.id.action_accountFragment_to_changeAccountFragment);
        }
        if (item.getItemId() == R.id.nav_exit) {
            navController.navigate(R.id.action_accountFragment_to_startFragment);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}