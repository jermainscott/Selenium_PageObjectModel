package org.selenium.pom.tests;


import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.SneakyThrows;
import org.codehaus.groovy.util.ArrayIterator;
import org.selenium.pom.api.actions.CartApi;
import org.selenium.pom.base.BaseTest;
import org.selenium.pom.objects.BillingAddress;
import org.selenium.pom.pages.CartPage;
import org.selenium.pom.pages.CheckoutPage;
import org.selenium.pom.utils.JacksonUtils;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ThreadFactory;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.testng.Assert.assertTrue;


public class CouponValidationTest extends BaseTest {


    //    Assignment 1- Coupon Tests

//    Automate three tests as below:
//
//    Validate that coupon "freeship" gives free shipping
//
//    Validate that coupon "offcart5" gives flat $5 off on the entire cart
//
//    Validate that coupon "off25" gives 25% off on entire cart
//
//    For all the tests, use guest checkout. You don't have to login. Also do not place order. Just validate the before and after amount on the page. You may add data providers to validate with a single product as well as to validate with multiple products.
//




    @DataProvider(name = "couponTestData")
    public Iterator<TestData> getCouponTestData() throws IOException {
        return List.of(
                new TestData("freeship", 0.0, 1215, 1),// Free shipping
                new TestData("offcart5", -5.0, 1215, 1),     // $5 off
                new TestData("off25", -25.0, 1215, 1),       // 25% off on a $100 item (assuming)
                new TestData("freeship", 0.0, 1215, 2),      // Free shipping with multiple products
                new TestData("offcart5", -5.0, 1215, 2),     // $5 off with multiple products
                new TestData("off25", -50.0, 1215, 2)      // 25% off on multiple products
        ).iterator();

    }

    @Test
    public void validCoupon() throws IOException {
        getCouponTestData().forEachRemaining( x -> {
            try {
                validateCouponApplication(x.getCoupon(), x.getDiscountAmount(),x.getProductID(),x.getQuantity());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }


    @Getter
    @AllArgsConstructor
    public static class TestData {
        private String coupon;
        private Double discountAmount;
        private Integer productID;
        private Integer quantity;
     }


    @SneakyThrows
    @Test(dataProvider = "couponTestData")
    public void validateCouponApplication(String couponCode, double expectedDiscount, int productId, int quantity) {

            BillingAddress billingAddress = JacksonUtils.deserializeJson("myBillingAddress.json", BillingAddress.class);
            CheckoutPage checkoutPage = new CheckoutPage(getDriver()).load();

            CartApi cartApi = new CartApi();
            cartApi.addToCart(productId, quantity);
            injectCookiesToBrowser(cartApi.getCookies());

            CartPage cartPage = new CartPage(getDriver());
            cartPage.load();
            double totalBeforeCoupon = cartPage.getTotalAmount();
            System.out.println(totalBeforeCoupon);
            cartPage.applyCoupon(couponCode);
            Thread.sleep(Duration.ofSeconds(4));
            double totalAfterCoupon = cartPage.getTotalAmount();

            System.out.println(totalAfterCoupon);
            assertTrue(totalBeforeCoupon > totalAfterCoupon);



        // Step 1: Add product(s) to the cart using API
////        CartApi cartApi = new CartApi();
////        cartApi.addToCart(productId, quantity);
//
//        // Step 2: Load Cart Page
//        CartPage cartPage = new CartPage(getDriver());
//
//        cartPage.load("/store/");
//        cartApi.addToCart(productId, quantity);
//        cartPage.load();
//        cartPage.applyCoupon("freeship");
//
//
//
//
//        // Step 3: Inject cookies into the browser after loading the page
//        injectCookiesToBrowser(cartApi.getCookies());
//
//
//        // Step 4: Capture total amount before applying coupon
//        double totalBeforeCoupon = cartPage.getTotalAmount();
//
//        // Step 5: Apply coupon and validate discount
//        cartPage.applyCoupon(couponCode);
//        double totalAfterCoupon = cartPage.getTotalAmount();

        // Calculate the actual discount applied
        double actualDiscount = totalBeforeCoupon - totalAfterCoupon;

        // Step 5: Assert the expected discount matches the actual discount
//        Assert.assertEquals(actualDiscount, expectedDiscount, "Coupon discount validation failed for " + couponCode);

    }


}
