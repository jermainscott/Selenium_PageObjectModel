package org.selenium.pom.tests;


import lombok.SneakyThrows;
import org.selenium.pom.api.actions.CartApi;
import org.selenium.pom.base.BaseTest;
import org.selenium.pom.objects.BillingAddress;
import org.selenium.pom.pages.CheckoutPage;
import org.selenium.pom.utils.FakerUtils;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;



public class SalesTaxTest extends BaseTest {


    @DataProvider(name = "taxDataProvider")
    public Object[][] getTaxData() {
        return new Object[][]{
              {"California", 0.075, "CA"}, // 7.5% tax rate for California
               {"New York", 0.04, "NY"}, // 8.875% tax rate for New York
               {"Texas", 0.0624, "TX"} // 8.25% tax rate for Texas
        };
    }


    @SneakyThrows
    @Test(dataProvider = "taxDataProvider")
    public void validateSalesTaxForDifferentStates(String state, Double expectedTaxRate, String stateAbbreviation) {
        FakerUtils faker = new FakerUtils();
        // Load the checkout page
        CheckoutPage checkoutPage = new CheckoutPage(getDriver()).load();

        //Delivery fee
      //  final int deliveryFee = 5;

        // Add a product to the cart
        CartApi cartApi = new CartApi();
        cartApi.addToCart(1215, 1); // product ID and quantity
        injectCookiesToBrowser(cartApi.getCookies());

        // Load the checkout page again after injecting cookies
        checkoutPage.load();

        // Set the billing address with the state and zip code
        BillingAddress billingAddress = BillingAddress.builder()
                .firstName(faker.getFirstName())
                .lastName(faker.getLastName())
                .country("United States (US)")
                .addressLineOne(faker.getStreetAddress())
                .city(faker.getCity())
                .state(state)
                .postalCode(faker.getZIPCode())
                .email(faker.getEmailAddress())
                .build();


        System.out.println(billingAddress);
        checkoutPage.setBillingAddress(billingAddress);

        // Capture the tax amount displayed on the checkout page
        Double SubTotalAmountBeforeTax = checkoutPage.getSubtotalAmount();
        String taxAmount = checkoutPage.getTaxAmount(stateAbbreviation);


        // Calculate the expected tax based on the product price and the expected tax rate
       // System.out.println("Total amount before tax "+ t);
        String expectedTaxAmount = String.format("%.2f",SubTotalAmountBeforeTax * expectedTaxRate);



        // Validate that the calculated tax matches the expected tax
        Assert.assertEquals(taxAmount, expectedTaxAmount, "Sales tax validation failed for state: " + state);

    }

}



//    @Test
//    public void GuestCheckoutUsingDirectBankTransfer() throws IOException, InterruptedException {
//        BillingAddress billingAddress = JacksonUtils.deserializeJson("myBillingAddress.json", BillingAddress.class);
//        CheckoutPage checkoutPage = new CheckoutPage(getDriver()).load();
//
//        CartApi cartApi = new CartApi();
//        cartApi.addToCart(1215, 1);
//        injectCookiesToBrowser(cartApi.getCookies());
//
//        checkoutPage.load().
//                setBillingAddress(billingAddress).
//                selectDirectBankTransfer().
//                placeOrder();
//        Assert.assertEquals(checkoutPage.getNotice(), "Thank you. Your order has been received.");
//
//    }



