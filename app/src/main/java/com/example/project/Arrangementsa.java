
package com.example.project;
public class Arrangementsa {
    private String title;
    private String price;
    private int imageResource;

    public Arrangementsa(String title, String price, int imageResource) {
        this.title = title;
        this.price = price;
        this.imageResource = imageResource;
    }

    public String getTitle() { return title; }
    public String getPrice() { return price; }
    public int getImageResource() { return imageResource; }
}
