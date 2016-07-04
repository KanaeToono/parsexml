package com.example.conga.parsexml;

/**
 * Created by ConGa on 8/03/2016.
 */
public class StackSite {
    private String name;
    private String link;
    private String about;
    private String imageUrl;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Override
    public String toString() {
        return "StackSite [name=" +name+ ", link " +link+ ", about"
                +about +", image" +imageUrl+"";
    }
}
