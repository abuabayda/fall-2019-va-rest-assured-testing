package com.cbt.pojos;

public class Spartans {
    private int id;
    private String name;
    private String gender;
    private int phone;

    @Override
    public String toString() {
        return "Spartans{" +
             "id=" + id +
             ", name='" + name + '\'' +
             ", gender='" + gender + '\'' +
             ", phone=" + phone +
             '}';
    }
}
