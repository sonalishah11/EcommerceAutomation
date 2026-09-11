package com.ecommerce.tests;

import com.ecommerce.utilities.ConfigReader;

public class App {

    public static void main(String[] args) {

        System.out.println(ConfigReader.getProperty("browser"));
        System.out.println(ConfigReader.getProperty("url"));
        System.out.println(ConfigReader.getProperty("username"));
        System.out.println(ConfigReader.getProperty("password"));

    }

}