package org.selenium.pom.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.selenium.pom.base.BasePage;
import org.openqa.selenium.By;

import java.time.Duration;

public class CartPage extends BasePage {

/*    private final By productName = By.cssSelector("td[class='product-name'] a");
    private final By checkoutBtn = By.cssSelector(".checkout-button");
    private final By cartHeading = By.cssSelector(".has-text-align-center");*/

    private final By totalAmountLocator = By.cssSelector(".order-total");
    private final By couponFieldLocator = By.cssSelector("#coupon_code");
    private final By applyCouponButtonLocator = By.cssSelector("button[name='apply_coupon']");
    private final By cartUpdatedMessageLocator = By.cssSelector(".woocommerce-message");

    @FindBy(css = "td[class='product-name'] a") private WebElement productName;
    @FindBy(how = How.CSS, using = ".checkout-button") @CacheLookup private WebElement checkoutBtn;


    public CartPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

   //created for Mentor Assignment
    public String getButton() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement taxElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("button[value='Apply coupon']")));
        return taxElement.getText();
    }



    // Load the cart page
    public CartPage load() {
        load("/cart/");
        return this;
    }

    public String getProductName(){
        return wait.until(ExpectedConditions.visibilityOf(productName)).getText();
    }

    public CheckoutPage checkout(){
        wait.until(ExpectedConditions.elementToBeClickable(checkoutBtn)).click();
        return new CheckoutPage(driver);
    }


    // Method to get the total amount
    public double getTotalAmount() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(2));
        WebElement totalElement = wait.until(ExpectedConditions.visibilityOfElementLocated(totalAmountLocator));
        String totalText = totalElement.getText();
        return parsePrice(totalText);
    }

//    // Method to apply a coupon
//    public CartPage applyCoupon(String couponCode) {
//        WebElement couponField = driver.findElement(couponFieldLocator);
//        couponField.clear();
//        couponField.sendKeys(couponCode);
//
//        WebElement applyCouponButton = driver.findElement(applyCouponButtonLocator);
//        applyCouponButton.click();
//
//        // Wait for the cart to update after applying the coupon
//        waitForCartUpdate();
//
//        return this;
//    }


    // New Method to apply a coupon
    public void applyCoupon(String couponCode) {
        if (couponCode == null || couponCode.isEmpty()) {
            throw new IllegalArgumentException("Coupon code cannot be null or empty");
        }

        WebElement couponField = driver.findElement(couponFieldLocator);
        couponField.clear();
        couponField.sendKeys(couponCode);

        WebElement applyCouponButton = driver.findElement(applyCouponButtonLocator);
        applyCouponButton.click();

        // Wait for the cart to update after applying the coupon
        waitForCartUpdate();

    }



    // Helper method to parse the price string to a double
    private double parsePrice(String priceText) {

        priceText = priceText.replaceAll("[^\\d.]", ""); // Remove all non-numeric characters
        return Double.parseDouble(priceText);
    }


    // Method to wait for the cart to update after applying a coupon
    private void waitForCartUpdate() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(cartUpdatedMessageLocator));
    }


}
