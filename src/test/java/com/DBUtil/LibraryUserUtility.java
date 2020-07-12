package com.DBUtil;

import com.github.javafaker.Faker;

import java.util.HashMap;
import java.util.Map;

public class LibraryUserUtility {

    public static Map<String,?> createUser(int userGroup){

        Faker faker= new Faker();
        String fullName = faker.name().fullName();
        String emailAddress = faker.internet().emailAddress();
        String address = faker.address().fullAddress();

        Map<String,Object> user = new HashMap<>();
        user.put("full_name",fullName);
        user.put("email",emailAddress);
        user.put("password",faker.number().digits(10));
        user.put("user_group_id",userGroup);
        user.put("status","active");
        user.put("start_date","2020-05-05");
        user.put("end_date","2021-05-05");
        user.put("address",address);
        return user;

    }

    public static void main(String [] args ){
        System.out.println(createUser(2));
    }
}
