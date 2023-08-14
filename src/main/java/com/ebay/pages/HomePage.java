package com.ebay.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class HomePage extends BasePage{

    public HomePage(WebDriver driver){
        super(driver);
    }

    public SearchResultPage searchField(String searchText) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@placeholder='Search for anything']"))).sendKeys(searchText);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='gh-btn']"))).click();
        return new SearchResultPage(driver);
    }

    public boolean validateTheCorrectPage() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[normalize-space()='Home']"))).isDisplayed();
    }

}
