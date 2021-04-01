package com.example.gymhelper;

import android.os.Bundle;
import android.text.InputType;
import android.text.method.TextKeyListener;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.gymhelper.ViewModel.ProjectViewModel;
import com.example.gymhelper.databinding.ActivityAddProjectBinding;

public class AddProjectActivity extends AppCompatActivity {

    private ActivityAddProjectBinding binding;
    private String exercise, intensity;
    private int sets, reps1, reps2, reps3, reps4;
    private final String[] intensityName = {"Light", "Medium", "Hard", "Extreme"};
    private Integer[] setsCount = {1, 2, 3, 4};
    private ProjectViewModel projectViewModel;
    private ProjectModel projectModel;
    private boolean isEdit = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityAddProjectBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initDropDownIntensity();
        initDropDownSets();
        projectViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(ProjectViewModel.class);

        if (getIntent().hasExtra("model")) {
            projectModel = getIntent().getParcelableExtra("model");
            binding.editExercise.setText(projectModel.exercise);
            binding.editIntensity.setText(String.valueOf(projectModel.intensity));
            binding.editSets.setText(String.valueOf(projectModel.sets));
            binding.editReps1.setText(String.valueOf(projectModel.reps1));
            binding.editReps2.setText(String.valueOf(projectModel.reps2));
            binding.editReps3.setText(String.valueOf(projectModel.reps3));
            binding.editReps4.setText(String.valueOf(projectModel.reps4));
            isEdit = true;
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        binding.btnAddProject.setOnClickListener(view -> {

            if (isEdit) {
               if (checkAllConditions()) {
                   projectModel.exercise = exercise;
                   projectModel.reps1 = reps1;
                   projectModel.reps2 = reps2;
                   projectModel.reps3 = reps3;
                   projectModel.reps4 = reps4;
                   projectModel.sets = sets;
                   projectModel.intensity = intensity;
                   projectViewModel.updateProject(projectModel);
                   Toast.makeText(this, "Updated", Toast.LENGTH_SHORT).show();
                   finish();
               }
            } else {
                if (checkAllConditions()) {
                    projectModel = new ProjectModel();
                    projectModel.exercise = exercise;
                    projectModel.reps1 = reps1;
                    projectModel.reps2 = reps2;
                    projectModel.reps3 = reps3;
                    projectModel.reps4 = reps4;
                    projectModel.sets = sets;
                    projectModel.intensity = intensity;
                    projectViewModel.insertProject(projectModel);
                    Toast.makeText(this, "Created", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });


    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();

    }

    private void initDropDownIntensity() {
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, intensityName);
        binding.editIntensity.setAdapter(arrayAdapter);
        binding.editIntensity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                intensity = (String) adapterView.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void initDropDownSets() {
        ArrayAdapter<Integer> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, setsCount);
        binding.editSets.setAdapter(arrayAdapter);
        binding.editSets.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                setsCount = (Integer[]) adapterView.getItemAtPosition(position);
                if (position == 3) {
                    binding.editReps4.setVisibility(View.INVISIBLE);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private boolean checkAllConditions() {
        if (binding.editExercise.getText().toString().trim().equals("")) {
            Toast.makeText(this, "Please enter Exercise name", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (binding.editReps1.getText().toString().trim().equals("") || binding.editReps2.getText().toString().trim().equals("") ||
                binding.editReps3.getText().toString().trim().equals("") || binding.editReps4.getText().toString().trim().equals("")) {
            Toast.makeText(this, "You did not enter Reps results", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (binding.editSets.getText().toString().trim().equals("")) {
            Toast.makeText(this, "Please chose Sets quantity from list", Toast.LENGTH_SHORT).show();
            return false;
        }
        else {
            sets = Integer.parseInt(binding.editSets.getText().toString().trim());
            intensity = binding.editIntensity.getText().toString().trim();
            exercise = binding.editExercise.getText().toString().trim();
            reps1 = Integer.parseInt(binding.editReps1.getText().toString().trim());
            reps2 = Integer.parseInt(binding.editReps2.getText().toString().trim());
            reps3 = Integer.parseInt(binding.editReps3.getText().toString().trim());
            reps4 = Integer.parseInt(binding.editReps4.getText().toString().trim());
            return true;
        }
    }

}
