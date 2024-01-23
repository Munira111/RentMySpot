package com.example.rentmyspot;

import android.graphics.BitmapFactory;

import java.util.Arrays;
//
public class Seating {

    String userneme;
    String Sname;
    String Scategory;
    int Sprice;
    String Sdescription;
     byte[] imageData;

    public Seating(String userneme, String Sname, String Scatogary, int Sprice, String Sdescription,byte[] imageData ) {
       this.userneme = userneme;
        this.Sname = Sname;
        this.Scategory = Scatogary;
        this.Sprice = Sprice;
        this.Sdescription = Sdescription;
        this.imageData = imageData;
    }

    public Seating(){}


    public String getUserneme() {
        return userneme;
    }

    public void setUserneme(String userneme) {
        this.userneme = userneme;
    }

    public String getSname() {
        return Sname;
    }

    public void setSname(String sname) {
        Sname = sname;
    }

    public String getScategory() {
        return Scategory;
    }

    public void setScategory(String scatogary) {
        Scategory = scatogary;
    }

    public int getSprice() {
        return Sprice;
    }

    public void setSprice(int sprice) {
        Sprice = sprice;
    }

    public String getSdescription() {
        return Sdescription;
    }

    public void setSdescription(String sdescription) {
        Sdescription = sdescription;
    }

    public byte[] getImageData() {
        return imageData;
    }

    public void setImageData(byte[] imageData) {
        this.imageData = imageData;
    }


    @Override
    public String toString() {
        return "Seating Information\n"
                + "user Name: " + userneme
                + "\nSeating Name: " + Sname
                + "\nSeating Category: " + Scategory
                + "\nSeating Price: " + Sprice
                + "\nSeating Description: " + Sdescription
//                + "Seating Image: " + Arrays.toString(imageData)
                ;
    }
}
