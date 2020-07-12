package com.cbt.tests.day8_More_Serial;

import com.cbt.pojos.Donuts;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class WriteEx {
    /*
    Gson -> library to serialize and deserialize
    GsonBuilder --> class that help create gson object with different options
     */
    @Test
    public void writeToJsonFile() throws IOException, IOException {
        // create object
        Donuts donut = new Donuts("cake donut", 12, true);

        System.out.println("donut = " + donut);

        // write to file to exclude some options serialize it
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();

        FileWriter fileWriter = new FileWriter("src/test/resources/old_donut.json");

        gson.toJson(donut,fileWriter);

        fileWriter.flush();
        fileWriter.close();
    }

    @Test
    public void readFromOldDonutFile() throws IOException {

        //read from json file to convert to object
        FileReader fileReader = new FileReader("src/test/resources/old_donut.json");

        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();

        Donuts donuts = gson.fromJson(fileReader,Donuts.class);
        System.out.println(donuts);

        /*
         /*
    CUSTOM MAPPER EXAMPLE FOR REST ASSURED
    this is how you can coonfigure your rest assured to use Gson with
    excludeFieldsWithoutExposeAnnotation
     ObjectMapperConfig config = new ObjectMapperConfig(ObjectMapperType.GSON)
                .gsonObjectMapperFactory(
                        (type, s) -> new GsonBuilder()
                                .excludeFieldsWithoutExposeAnnotation()
                                .create());
        RestAssured.config = RestAssuredConfig.config().objectMapperConfig(config);
        Response response = get("/student/{id}", 11613);
        // assume we have student pojo with @expose annotation
        Student student = response.jsonPath().getObject("students[0]", Student.class);
        System.out.println(student);
     */

    }
}
