package com.example.tomek.moodestimation;

import java.sql.Blob;

/**
 * Created by Tomek on 10/14/2016.
 */
public class FeelingModel {
    private int idFeeling;
    private String name;
    private int percentValue;
    private Blob image;

    public FeelingModel(){}
    public FeelingModel(int id, String name, int percentValue, Blob image){
        this.idFeeling =id;
        this.name=name;
        this.percentValue = this.percentValue;
        this.image=image;
    }

    public int getIdFeeling() {
        return idFeeling;
    }

    public void setIdFeeling(int idFeeling) {
        this.idFeeling = idFeeling;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPercentValue() {
        return percentValue;
    }

    public void setPercentValue(int percentValue) {
        this.percentValue = percentValue;
    }

    public Blob getImage() {
        return image;
    }

    public void setImage(Blob image) {
        this.image = image;
    }
}
