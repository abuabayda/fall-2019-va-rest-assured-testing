package com.cbt.pojos;

public class Car {
    private String make;
    private String model;
    private int doors;
    private double price;

    @Override
    public String toString() {
        return "Car{" +
             "make='" + make + '\'' +
             ", model='" + model + '\'' +
             ", doors=" + doors +
             ", price=" + price +
             '}';
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getDoors() {
        return doors;
    }

    public void setDoors(int doors) {
        this.doors = doors;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
