package com.dawson.jonat.stockers.ContactDialog;

import android.graphics.Bitmap;

public class ContactInformation {
    public String contactName;
    private Bitmap image;

    public ContactInformation(String name, Bitmap image) {
        contactName = name;
    }

    public Bitmap getImage() {
        return image;
    }
}
