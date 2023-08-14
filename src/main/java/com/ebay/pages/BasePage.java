package com.ebay.pages;

import lombok.NoArgsConstructor;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.concurrent.TimeUnit;

@NoArgsConstructor
public abstract class BasePage {

    protected WebDriver driver;
    protected WebDriverWait wait;
    protected Wait<WebDriver> fluentWait;

    public BasePage(WebDriver driver) {
        super();
        this.driver = driver;
        this.wait = new WebDriverWait(driver, 20);
        this.fluentWait = new FluentWait<WebDriver>(driver).withTimeout(40, TimeUnit.SECONDS)
                .pollingEvery(3, TimeUnit.SECONDS).ignoring(NoSuchElementException.class);
    }

}
