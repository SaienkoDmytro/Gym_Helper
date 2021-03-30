package com.example.gymhelper;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.gymhelper.Adapter.ProjectAdapter;
import com.example.gymhelper.ViewModel.ProjectViewModel;
import com.example.gymhelper.databinding.ActivityMainBinding;


public class MainActivity extends AppCompatActivity implements OnClickItemInterface {

    private ProjectViewModel projectViewModel;
    private ProjectAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        com.example.gymhelper.databinding.ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.projectRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ProjectAdapter(this);
        binding.projectRecyclerView.setAdapter(adapter);

        binding.addProject.setOnClickListener(view -> startActivity(new Intent(MainActivity.this, AddProjectActivity.class)));


        projectViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(ProjectViewModel.class);

        projectViewModel.getAllProjectLive().observe(MainActivity.this, projectModelList -> {
            if (projectModelList != null) {
                adapter.setProjects(projectModelList);

            }
        });


    }

    @Override
    public void onClickItem(ProjectModel projectModel, boolean isEdit) {


        if (isEdit) {
            Intent intent = new Intent(MainActivity.this, AddProjectActivity.class);
            intent.putExtra("model", projectModel);
            startActivity(intent);
        } else {
            projectViewModel.deleteProject(projectModel);
        }

    }
}