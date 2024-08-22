package org.selenium.pom.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.selenium.pom.base.BasePage;

import java.time.Duration;

public class AccountPage extends BasePage {
    private final By usernameFld =  By.cssSelector("#username");
    private final By passwordFld =  By.cssSelector("#password");
    private final By loginBtn =  By.cssSelector("button[value='Log in']");
    private final By errorMessage = By.xpath("//ul[@class='woocommerce-error']/child::li");

    public AccountPage(WebDriver driver) {
        super(driver);
    }



    public AccountPage login(String username, String password){
        wait.until(ExpectedConditions.visibilityOfElementLocated(usernameFld)).sendKeys(username);
        driver.findElement(passwordFld).sendKeys(password);
        driver.findElement(loginBtn).click();
        return this;
    }





    // for practice
    public String login() {

//
//        wait.until(ExpectedConditions.visibilityOfElementLocated(usernameFld)).sendKeys(username);
//        driver.findElement(passwordFld).sendKeys(password);
//        driver.findElement(loginBtn).click();
       WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement loginBtn = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("button[value='Log in']")));
        return loginBtn.getText();


    }



    public AccountPage load(){
        load("/account/");
        return this;
    }

    public String getErrorTxt(){
        return driver.findElement(errorMessage).getText();
    }


}