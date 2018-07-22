package com.vynaloze.smartmirror.model.randomcomment.pojo;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;

import com.vynaloze.smartmirror.model.randomcomment.converters.PartOfDayConverter;

@Entity
public class RandomComment {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String text;
    private double probability;
    @TypeConverters(PartOfDayConverter.class)
    private PartOfDay partOfDay;

    @Ignore
    public RandomComment(String text, double probability, PartOfDay partOfDay) {
        this.text = text.replace("\\n", System.getProperty("line.separator"));
        this.probability = probability;
        this.partOfDay = partOfDay;
    }

    public RandomComment(int id, String text, double probability, PartOfDay partOfDay) {
        this.id = id;
        this.text = text;
        this.probability = probability;
        this.partOfDay = partOfDay;
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

    public PartOfDay getPartOfDay() {
        return partOfDay;
    }

    public void setPartOfDay(PartOfDay partOfDay) {
        this.partOfDay = partOfDay;
    }

    @Override
    public String toString() {
        return "RandomComment{" +
                "id=" + id +
                ", text='" + text + '\'' +
                ", probability=" + probability +
                ", partOfDay=" + partOfDay +
                '}';
    }
}
