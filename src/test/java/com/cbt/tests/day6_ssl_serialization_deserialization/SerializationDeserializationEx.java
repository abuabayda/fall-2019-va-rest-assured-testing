package com.cbt.tests.day6_ssl_serialization_deserialization;

import com.cbt.pojos.Car;
import com.cbt.pojos.Donuts;
import com.cbt.pojos.Spartan;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.*;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class SerializationDeserializationEx {

    //read file
  @Test
  public void readToMap() throws FileNotFoundException {
    // this is will read file
    FileReader fileReader = new FileReader("src/test/resources/car.json");

    //convert file into an object that we can use more easily,
    // create map from this
    //conversion using Gson
    //in our framework we use Gason
    //json ---> file type (xml, pdf, doc)
    //gson --> library used for conversion (serialization)


    Gson gson = new Gson();

    //we converted file to map
    //we deserialization file to java object
    Map<String,?> myCar = gson.fromJson(fileReader,Map.class);
    System.out.println(myCar);
    System.out.println(myCar.get("doors"));
    System.out.println(myCar.get("make"));
    System.out.println(myCar.get("model"));
  }

  @Test
  public void readToObject() throws FileNotFoundException {
    //reading json file
    FileReader fileReader = new FileReader("src/test/resources/car.json");

    //de-serialization
    Gson gson = new Gson();
    //reading dat from car class
    Car car = gson.fromJson(fileReader, Car.class);
    System.out.println(car.getMake());
    System.out.println(car.getDoors());
    System.out.println(car.getPrice());
  }

  @Test
  public void writeToJsonFile() throws IOException {
    //create object

    Donuts donuts = new Donuts("cake", 12,true);
    System.out.println("donuts = " + donuts);




//    Car myCar = new Car();
//    myCar.setMake("Corolla");
//    myCar.setModel("2004 one");
//    myCar.setDoors(4);
//    myCar.setPrice(98);
//    System.out.println("myCar = " + myCar);
//
//    //write to file serialize it
//
    Gson gson = new GsonBuilder().setPrettyPrinting().create();
    FileWriter fileWriter=
            new FileWriter("src/test/resources/New_dounts.json");

    gson.toJson(donuts,fileWriter);

    fileWriter.flush();
    fileWriter.close();
  }

  @Test
  public void readAndWriteItBack() throws IOException {
    FileReader reader =
              new FileReader("src/test/resources/car.json");

    Gson gson = new Gson();
    Car car = gson.fromJson(reader, Car.class);
    System.out.println(car);

    car.setMake("Mercedes");
    car.setModel("C400");
    car.setPrice(80);
    car.setDoors(4);

    System.out.println(car);

    FileWriter fileWriter =
              new FileWriter("src/test/resources/another_car.json");
    gson = new  GsonBuilder().setPrettyPrinting().create();

    gson.toJson(car,fileWriter);

    fileWriter.flush();
    fileWriter.close();
    reader.close();
  }

  @Test
  public void spartans(){
    baseURI = "http://54.146.89.247:8000/api/";
    Response response = given().
         auth().basic("admin","admin").
         pathParams("id",138).
     when().
         get("/spartans/{id}");
    response.prettyPeek();
    //as()---> de serialize response into given type
    response.as(Spartan.class);
    Spartan spartan = response.as(Spartan.class);
    System.out.println(spartan);

    Map<String,?> spartanMap = response.as(Map.class);
    System.out.println(spartan);
  }
}
