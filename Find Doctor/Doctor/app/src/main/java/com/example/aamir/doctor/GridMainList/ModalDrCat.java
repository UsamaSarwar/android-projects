package com.example.aamir.doctor.GridMainList;

/**
 * Created by Aamir on 5/27/2017.
 */

public class ModalDrCat {

    private int catId;
    private String catImage;
    private String catName;

    public ModalDrCat(int id,String catImage, String catName) {
        this.catImage = catImage;
        this.catName = catName;
        this.catId = id;
    }

    public int getCatId() {
        return catId;
    }

    public String getCatImage() {
        return catImage;
    }

    public String getCatName() {
        return catName;
    }

}
