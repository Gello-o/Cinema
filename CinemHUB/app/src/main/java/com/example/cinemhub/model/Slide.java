package com.example.cinemhub.model;

public class Slide {

    private int image ;
    private String title;
    // Add more field depand on whay you wa&nt ...


    public Slide(int image, String title) {
        this.image = image;
        this.title = title;
    }


    public int getImage() {
        return image;
    }

    public String getTitle() {
        return title;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}