package com.cbt.day6_ssl_serialization_deserialization;

import com.google.gson.Gson;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.FileReader;
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

    



  }
}
