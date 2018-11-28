package com.dawson.jonat.stockers.ContactDialog;

import android.graphics.Bitmap;

public class ContactInformation {
    public String contactName;
    private String image;

    public ContactInformation(String name, String image) {
        contactName = name;
        this.image = image;
    }

    public String getImage() {
        return image;
    }
}
