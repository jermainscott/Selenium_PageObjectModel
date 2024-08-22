package org.selenium.pom.tests;

import io.opentelemetry.exporter.logging.SystemOutLogRecordExporter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.selenium.pom.base.BaseTest;
import org.selenium.pom.objects.Product;
import org.selenium.pom.pages.CartPage;
import org.selenium.pom.pages.CheckoutPage;
import org.selenium.pom.pages.StorePage;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.time.Duration;

public class ApplyButton  extends BaseTest {

    @Test
    public void addToCartFromStorePage() throws IOException {
        Product product = new Product(1215);
        CartPage cartPage = new StorePage(getDriver()).load().
                getProductThumbnail().clickAddToCartBtn(product.getName()).
                clickViewCart();
       // Assert.assertEquals(cartPage.getProductName(), product.getName());
//        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
//        WebElement taxElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//tr[contains(@class, 'tax-rate')]//td//span[@class='woocommerce-Price-amount amount']")));

   // ApplyButton applyButton =new ApplyButton();

        String applyB = cartPage.getButton();
        System.out.println("The ApplyButton is working" + applyB);

        Assert.assertEquals(cartPage.getButton(),"APPLY COUPON");


    }


}
