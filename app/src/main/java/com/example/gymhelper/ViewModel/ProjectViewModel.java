package com.example.gymhelper.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.gymhelper.ProjectModel;
import com.example.gymhelper.Repository.AppRepo;

import java.util.List;

public class ProjectViewModel extends AndroidViewModel {

    private final AppRepo appRepo;

    public ProjectViewModel(@NonNull Application application) {
        super(application);

        appRepo = new AppRepo(application);
    }

    public void insertProject(ProjectModel projectModel) {
        appRepo.insertProject(projectModel);
    }

    public void updateProject(ProjectModel projectModel) {
        appRepo.updateProject(projectModel);
    }

    public void deleteProject(ProjectModel projectModel) {
        appRepo.deleteProject(projectModel);
    }

    public LiveData<List<ProjectModel>> getAllProjectLive() {
        return appRepo.getAllProjectLive();
    }


}