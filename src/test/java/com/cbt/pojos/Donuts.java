package com.cbt.pojos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Donuts {
    @SerializedName("donutID")
    @Expose(serialize = false)
    private int donutId;
    @Expose
    private  String name;
    @SerializedName("sugarPercentage")
    @Expose
    private int sugarPct;
    @Expose
    private boolean isGlutenFree;


    public int getId() {
        return donutId;
    }

    public void setId(int id) {
        this.donutId = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSugarPct() {
        return sugarPct;
    }

    public void setSugarPct(int sugarPct) {
        this.sugarPct = sugarPct;
    }

    public boolean isGlutenFree() {
        return isGlutenFree;
    }

    public void setGlutenFree(boolean glutenFree) {
        isGlutenFree = glutenFree;
    }

    public Donuts(String name, int sugarPct, boolean isGlutenFree) {
        this.name = name;
        this.sugarPct = sugarPct;
        this.isGlutenFree = isGlutenFree;
    }
    @SerializedName("donutId")
    @Override
    public String toString() {
        return "Donuts{" +
             "donutId=" + donutId +
             ", name='" + name + '\'' +
             ", sugarPct=" + sugarPct +
             ", isGlutenFree=" + isGlutenFree +
             '}';
    }
}
