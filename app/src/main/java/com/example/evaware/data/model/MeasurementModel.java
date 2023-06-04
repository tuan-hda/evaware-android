package com.example.evaware.data.model;

public class MeasurementModel {


    private int id;
    private int depth;
    private int height;

    private float weight;

    private int width;

    public int getDepth() {
        return depth;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public int getWidth() {
        return width;
    }

    @Override
    public String toString() {
        return "MeasurementModel{" +
                "id=" + id +
                ", depth=" + depth +
                ", height=" + height +
                ", weight=" + weight +
                ", width=" + width +
                '}';
    }

    public void setWidth(int width) {
        this.width = width;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public MeasurementModel(int id, int depth, int height, float weight, int width) {
        this.id = id;
        this.depth = depth;
        this.height = height;
        this.weight = weight;
        this.width = width;
    }

    public MeasurementModel() {

    }
}
