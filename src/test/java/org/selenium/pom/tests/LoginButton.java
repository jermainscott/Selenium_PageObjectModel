package org.selenium.pom.tests;

import org.openqa.selenium.support.ui.ExpectedConditions;
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

import static org.bouncycastle.cms.RecipientId.password;

public class LoginButton extends BaseTest {

// This Test is For My Mentor Assessment
    @Test()
    public void shouldNotLoginWithAnInvalidPassword(){
        String username = "janedoe" + new FakerUtils().generateRandomNumber();
        User user = new User(username, "demopwd", username + "@askomdch.com");
        new SignUpApi().register(user);

        AccountPage accountPage = new AccountPage(getDriver()).load();
        accountPage.login();


        String loginB =  accountPage.login();
        System.out.println("This " + loginB + " is working fine");
        Assert.assertEquals(accountPage.login(), "LOG IN");

    }


}
