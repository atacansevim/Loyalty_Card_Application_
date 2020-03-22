package com.example.loyalty_card_application;

public class ListViewElement {

    private String name;
    private int image;

    public ListViewElement(String name,int image) {
        this.setName(name);
        this.setImage(image);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
