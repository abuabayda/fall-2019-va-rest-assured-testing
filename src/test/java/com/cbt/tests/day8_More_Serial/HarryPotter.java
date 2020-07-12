package com.cbt.tests.day8_More_Serial;


import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.*;

public class HarryPotter {
    @Test
    public void getAllNames(){
        baseURI = "https://www.potterapi.com/v1/";
        JsonPath jsonPath = given().queryParam("key","$2a$10$I3hu9b.nwxgrGgyixnZmb.1YeSV5Lr0lojB3KcLmtKWfdZ4uhs4Ky"). 
             when(). 
             get("characters").jsonPath();

        List<String> name = jsonPath.getList("name");
        System.out.println("Number of names: " + name.size());

        List<Map> people = jsonPath.getList("",Map.class);
        System.out.println("people.size() = " + people.size());

        System.out.println(people.get(0));

        List<Character> characters = jsonPath.getList("name",Character.class);

        Character character = characters.get(0);


    }


}
