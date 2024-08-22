package org.selenium.pom.utils;

import com.github.javafaker.Faker;

public class FakerUtils {

    private final Faker faker = new Faker();
    public Long generateRandomNumber(){
        return faker.number().randomNumber(10, true);
    }

    public String generateRandomName(){
        return faker.name().fullName();
    }

    public String getFirstName() {
        return faker.name().firstName();
    }
    public String getLastName() {
        return faker.name().lastName();
    }

    public String getStreetAddress() {
        return faker.address().streetAddress();
    }


    public String getCity() {
        return faker.address().city();
    }


    public String getZIPCode() {
        return faker.address().zipCode();
    }


    public String getEmailAddress() {
        return faker.name().fullName().strip() + "@gmail.com";
    }





}
