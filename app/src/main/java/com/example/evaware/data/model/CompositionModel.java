package com.example.evaware.data.model;

public class CompositionModel {
    private String main_material;
    private String weight;
    private String id;

    public String getMain_material() {
        return main_material;
    }

    public void setMain_material(String main_material) {
        this.main_material = main_material;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public CompositionModel(String main_material, String weight, String id) {
        this.main_material = main_material;
        this.weight = weight;
        this.id = id;
    }

    @Override
    public String toString() {
        return "CompositionModel{" +
                "main_material='" + main_material + '\'' +
                ", weight='" + weight + '\'' +
                ", id='" + id + '\'' +
                '}';
    }

    public CompositionModel(){

    }
}
