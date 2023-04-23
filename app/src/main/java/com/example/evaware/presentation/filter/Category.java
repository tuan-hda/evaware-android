package com.example.evaware.presentation.filter;

public class Category {
    String name;
    String selected;

    public Category() {
        this("Category", "Selected");
    }

    public Category(String name, String selected) {
        this.name = name;
        this.selected = selected;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSelected() {
        return selected;
    }

    public void setSelected(String selected) {
        this.selected = selected;
    }
}
