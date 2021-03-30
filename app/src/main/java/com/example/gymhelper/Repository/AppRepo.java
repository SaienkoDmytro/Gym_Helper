package com.example.gymhelper.Repository;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.example.gymhelper.Database.AppDatabase;
import com.example.gymhelper.ProjectModel;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class AppRepo {

    private final AppDatabase appDatabase;
    private final Executor executor = Executors.newSingleThreadExecutor();

    public AppRepo(Context context) {
        appDatabase = AppDatabase.getInstance(context);
    }


    public void insertProject(ProjectModel projectModel) {

        executor.execute(() -> appDatabase.projectDao().insertProject(projectModel));

    }

    public void updateProject(ProjectModel projectModel) {
        executor.execute(() -> appDatabase.projectDao().updateProject(projectModel));
    }

    public void deleteProject(ProjectModel projectModel) {
        executor.execute(() -> appDatabase.projectDao().deleteProject(projectModel));
    }


    public LiveData<List<ProjectModel>> getAllProjectLive()  {

        return appDatabase.projectDao().getAllProjectsLive();


    }

}