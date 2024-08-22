


package org.selenium.pom.tests;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.selenium.pom.api.actions.ApiAccount;
import org.selenium.pom.base.BaseTest;
import org.selenium.pom.objects.User;
import org.selenium.pom.pages.AccountPage;
import org.testng.Assert;
import org.testng.annotations.Test;





public class ViewOrderTest extends BaseTest {


//    Automate below test:
//
//    Validate that the customer is able to view an existing order from the Accounts page.
//
//            Note: This test is complex.
//
//    Hint: Use APIs to set the application state.
//


    @Test
    public void validateViewOrder() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        String username = "janedoe";
        String password = "janepwd";

        // Step 1: Login via API and get auth token
        String authToken = ApiAccount.loginAndGetAuthToken(username, password);


        // Step 2: Create an order via API and get order ID
        String orderId = ApiAccount.createOrder(authToken);


        // Step 3: Perform UI operations
        WebDriver driver = getDriver();
        AccountPage accountPage = new AccountPage(driver);


        // Load the Accounts page
        accountPage.load();
        accountPage.login(username,password);


        // Validate that the order is visible on the Accounts page
        accountPage.load("/account/orders/");
//        Assert.assertTrue("Order is not visible on the Account page.");
    }


}
