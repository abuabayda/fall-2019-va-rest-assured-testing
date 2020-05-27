package com.DBUtil;

import java.io.FileInputStream;

import java.util.Properties;

public class ConfigReader {

  private static Properties configFile;
  static {
    try {
      String path = "Config.properties";
       FileInputStream input = new FileInputStream(path);
        configFile = new Properties();
        configFile.load(input);
      } catch (Exception e) {
        throw  new RuntimeException(e);
      }
  }

  public static String getProperty(String keyName){
    return configFile.getProperty(keyName);
  }
}
