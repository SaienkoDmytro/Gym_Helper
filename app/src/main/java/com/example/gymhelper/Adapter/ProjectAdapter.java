package com.example.gymhelper.Adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gymhelper.OnClickItemInterface;
import com.example.gymhelper.ProjectModel;
import com.example.gymhelper.R;
import com.example.gymhelper.databinding.ProjectItemLayoutBinding;

import java.util.List;

public class ProjectAdapter extends RecyclerView.Adapter<ProjectAdapter.ViewHolder> {

    List<ProjectModel> projectModelList;
    private final OnClickItemInterface onClickItemInterface;

    public ProjectAdapter(OnClickItemInterface onClickItemInterface) {
        this.onClickItemInterface = onClickItemInterface;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ProjectItemLayoutBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.project_item_layout, parent, false);

        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        if (projectModelList != null) {
            ProjectModel projectModel = projectModelList.get(position);

            holder.binding.setProjectModel(projectModel);
            holder.binding.setListener(onClickItemInterface);
        }


    }

    @Override
    public int getItemCount() {

        if (projectModelList != null)
            return projectModelList.size();
        else return 0;
    }

    public void setProjects(List<ProjectModel> projects) {
        projectModelList = projects;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ProjectItemLayoutBinding binding;

        public ViewHolder(@NonNull ProjectItemLayoutBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}