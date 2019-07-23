package com.modele.badelcollection;

public class SliderModel {

    private String banner;
    //private String backgroundColor; // Api 21 poum ka ranjel 49 Firebase Video

    public SliderModel(String banner){//, String backgroundColor) {
        this.banner = banner;
        //this.backgroundColor = backgroundColor;
    }

    public String getBanner() {
        return banner;
    }

    public void setBanner(String banner) {
        this.banner = banner;
    }
    // solution video 16
   /* public String getBackgroundColor() {
        return backgroundColor;
    }*/

    /*public void setBackgroundColor(String backgroundColor) {
        this.backgroundColor = backgroundColor;
    }*/
}
