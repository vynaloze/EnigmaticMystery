package com.vynaloze.smartmirror.model.randomcomment.pojo;

public class RandomComment {
    private String text;
    private double probability;
    private PartOfDay partOfDay;

    public RandomComment(String text, double probability, PartOfDay partOfDay) {
        this.text = text.replace("\\n", System.getProperty("line.separator"));
        this.probability = probability;
        this.partOfDay = partOfDay;
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
                "text='" + text + '\'' +
                ", probability=" + probability +
                ", partOfDay=" + partOfDay +
                '}';
    }
}
