package com.example.loyalty_card_application;

public class GridViewElement {
    String name;
    String id;
    int bgcolor;
    int txtcolor;
    int height;
    int width;

    public GridViewElement(String name,String id, int bgcolor, int txtcolor, int height, int width) {
        this.name = name;
        this.id = id;
        this.bgcolor = bgcolor;
        this.txtcolor = txtcolor;
        this.height = height;
        this.width = width;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBgcolor() {
        return bgcolor;
    }

    public void setBgcolor(int bgcolor) {
        this.bgcolor = bgcolor;
    }

    public int getTxtcolor() {
        return txtcolor;
    }

    public void setTxtcolor(int txtcolor) {
        this.txtcolor = txtcolor;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }
}
