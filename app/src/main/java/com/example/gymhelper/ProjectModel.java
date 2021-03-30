package com.example.gymhelper;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "project")
public class ProjectModel implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    public int pId;

    @ColumnInfo(name = "p_title")
    public String exercise;
    public String intensity;
    public int sets;
    public int reps1;
    public int reps2;
    public int reps3;
    public int reps4;

    public ProjectModel() {
    }

    protected ProjectModel(Parcel in) {
        pId = in.readInt();
        exercise = in.readString();
        intensity = in.readString();
        sets = in.readInt();
        reps1 = in.readInt();
        reps2 = in.readInt();
        reps3 = in.readInt();
        reps4 = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(pId);
        dest.writeString(exercise);
        dest.writeString(intensity);
        dest.writeInt(sets);
        dest.writeInt(reps1);
        dest.writeInt(reps2);
        dest.writeInt(reps3);
        dest.writeInt(reps4);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ProjectModel> CREATOR = new Creator<ProjectModel>() {
        @Override
        public ProjectModel createFromParcel(Parcel in) {
            return new ProjectModel(in);
        }

        @Override
        public ProjectModel[] newArray(int size) {
            return new ProjectModel[size];
        }
    };

}
