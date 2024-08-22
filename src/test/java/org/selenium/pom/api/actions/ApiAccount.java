package org.selenium.pom.api.actions;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.selenium.pom.objects.User;

import java.awt.*;

import static org.bouncycastle.cms.RecipientId.password;


//   This is for Assignment 2: Account Tests

public class ApiAccount {
    static ObjectMapper ob = new ObjectMapper();

//    API for setting the application state

    public static String loginAndGetAuthToken(String username, String password) throws JsonProcessingException {
        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .formParams("username",username, "password",password)
                .post("https://askomdch.com/account/");

        return response.toString();

    }


    public static String createOrder(String authToken) {
        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + authToken)
                .body("")
                .post("https://askomdch.com/store/1215");

        return response.toString();
//        return response.jsonPath().getString("orderId");
    }



}
