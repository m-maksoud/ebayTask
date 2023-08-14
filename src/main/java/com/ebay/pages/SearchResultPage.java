package com.ebay.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

// page_url = about:blank
public class SearchResultPage extends BasePage {


    public SearchResultPage(WebDriver driver) {
        super(driver);
    }

    public String findResultsNumber() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated
                (By.cssSelector("div[class='srp-controls__control srp-controls__count'] span:nth-child(1)"))).getText();
    }

    public void selectTransmissionType(String transmission_type) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='"+transmission_type+"']"))).click();
    }

    public boolean validateObtainedResults(String searchText) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated
                (By.xpath("//span[normalize-space()='"+searchText+"']"))).isDisplayed();
    }
}