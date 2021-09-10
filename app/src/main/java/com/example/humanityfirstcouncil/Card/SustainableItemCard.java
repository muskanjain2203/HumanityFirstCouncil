package com.example.humanityfirstcouncil.Card;

public class SustainableItemCard {
    private int imageId;
    private String text;

    public SustainableItemCard(int image, String textV ){

        this.imageId=image;
        this.text = textV;
    }
    public int getImageId(){
        return imageId;
    }
    public String getText(){
        return text;
    }
}
