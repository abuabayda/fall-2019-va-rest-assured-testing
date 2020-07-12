package com.Homeworks.UINamesss;

public class UserPOJO {
    /*
    {
        "name": "Fevzi",
        "surname": "Yerman",
        "gender": "male",
        "region": "Turkey"
    },
     */

    private String name;
    private String surname;
    private String gender;
    private String  region;

    @Override
    public String toString() {
        return "UserPOJO{" +
             "name='" + name + '\'' +
             ", surname='" + surname + '\'' +
             ", gender='" + gender + '\'' +
             ", region='" + region + '\'' +
             '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }
}
