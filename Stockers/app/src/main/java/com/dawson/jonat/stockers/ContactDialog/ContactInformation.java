package com.dawson.jonat.stockers.ContactDialog;

import android.graphics.Bitmap;

public class ContactInformation {
    private String contactName;
    private String image;
    private String emailAddress;

    public ContactInformation(String name, String image, String emailAddress) {
        this.contactName = name;
        this.image = image;
        this.emailAddress = emailAddress;
    }

    public String getContactName() {
        return this.contactName;
    }

    public String getImage() {
        return image;
    }

    public String getEmailAddress() {
        return emailAddress;
    }
}
