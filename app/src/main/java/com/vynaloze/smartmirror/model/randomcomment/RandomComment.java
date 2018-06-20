package com.vynaloze.smartmirror.model.randomcomment;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class RandomComment {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String text;
    private double probability;

    @Ignore
    public RandomComment(String text, double probability) {
        this.text = text;
        this.probability = probability;
    }

    public RandomComment(int id, String text, double probability) {
        this.id = id;
        this.text = text;
        this.probability = probability;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public double getProbability() {
        return probability;
    }

    public void setProbability(double probability) {
        this.probability = probability;
    }

    @Override
    public String toString() {
        return "RandomComment{" +
                "id=" + id +
                ", text='" + text + '\'' +
                ", probability=" + probability +
                '}';
    }
}
