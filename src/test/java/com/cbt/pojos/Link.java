package com.cbt.pojos;

import com.google.gson.annotations.SerializedName;

public class Link {

    @SerializedName("rel")

    private String rel;
    @SerializedName("href")

    private String href;


    public String getRel() {
        return rel;
    }

    public void setRel(String rel) {
        this.rel = rel;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    @Override
    public String toString() {
        return "Link{" +
             "rel='" + rel + '\'' +
             ", href='" + href + '\'' +
             '}';
    }
}
