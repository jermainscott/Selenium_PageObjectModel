package org.selenium.pom.tests;

import lombok.Value;
import org.selenium.pom.api.actions.CartApi;
import org.selenium.pom.api.actions.SignUpApi;
import org.selenium.pom.base.BaseTest;
import org.selenium.pom.objects.Product;
import org.selenium.pom.objects.User;
import org.selenium.pom.pages.AccountPage;
import org.selenium.pom.pages.CheckoutPage;
import org.selenium.pom.utils.FakerUtils;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LoginTest extends BaseTest {



    @Test
    public void loginDuringCheckout() throws Exception {
        String username = "janedoe" + new FakerUtils().generateRandomNumber();
        User user = new User().
                setUsername(username).
                setPassword("janepwd").
                setEmail(username + "@askomdch.com");

        SignUpApi signUpApi = new SignUpApi();
        signUpApi.register(user);
        CartApi cartApi = new CartApi();
        Product product = new Product(1215);
        cartApi.addToCart(product.getId(), 1);

        CheckoutPage checkoutPage = new CheckoutPage(getDriver()).load();
        injectCookiesToBrowser(cartApi.getCookies());
        checkoutPage.load();
        checkoutPage.
                clickHereToLoginLink().
                login(user);
        Assert.assertTrue(checkoutPage.getProductName().contains(product.getName()));

    }


    // Assignment 1- Add More Test Cases
    @Test()
    public void shouldNotLoginWithAnInvalidPassword(){
        String username = "janedoe" + new FakerUtils().generateRandomNumber();
        User user = new User(username, "demopwd", username + "@askomdch.com");
        new SignUpApi().register(user);

        AccountPage accountPage = new AccountPage(getDriver()).load();
        accountPage.login(user.getUsername(), "invalidPassword");
        Assert.assertEquals(accountPage.getErrorTxt(), "Error: The password you entered for the username "
                + user.getUsername() + " is incorrect. Lost your password?");
    }



}
