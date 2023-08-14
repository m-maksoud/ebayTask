package com.ebay.utilities;

import org.checkerframework.checker.nullness.compatqual.NullableDecl;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Random;


public class Utilities {


    public static void waitAndClickOnWebElement(WebElement webElement, WebDriverWait wait, WebDriver driver) {
        try {
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", webElement);
            wait.until(ExpectedConditions.elementToBeClickable(webElement));
            webElement.click();
        } catch(NoSuchElementException | TimeoutException | ElementClickInterceptedException e ) {
            wait.until(ExpectedConditions.elementToBeClickable(webElement));
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", webElement);
            webElement.click();
        }
    }

    public static void waitUntilSelectHasOptions(WebElement webElement, WebDriverWait wait) {
        final Select dropdown = new Select(webElement);

        wait.until(new ExpectedCondition<Boolean>() {
            @NullableDecl
            @Override
            public Boolean apply(@NullableDecl WebDriver driver) {
                return (dropdown.getOptions().size() > 1);
            }
        });
    }

    public static void waitAndSelectByVisibleText(WebElement element, String value, WebDriverWait wait) {
        waitUntilSelectHasOptions(element, wait);
        Select dropdown = new Select(element);
        dropdown.selectByVisibleText(value);
    }

    public static boolean isClassPresent(WebElement element, String className) {
        Boolean result = false;
        try {
            String value = element.getAttribute("class");
            if (value != null && value.contains(className)) {
                result = true;
            }
        } catch (Exception e) {
            System.out.println("A Handled Exception is Thrown");
            e.printStackTrace();
        }

        return result;
    }

    public static boolean isFieldMandatory(WebElement element, String validClassName, String invalidClassName, WebDriverWait wait, WebDriver driver) {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(element));
            String fieldText = element.getAttribute("value");
            element.sendKeys(Keys.chord(Keys.CONTROL, "a"));
            for (int i = 0; i < 11; i++)
                element.sendKeys(Keys.BACK_SPACE);
            if (isClassPresent(element, invalidClassName)) {
                waitAndClickOnWebElement(element, wait, driver);
                int count = fieldText.length();
                if (fieldText.length() > 0)
                    element.sendKeys(fieldText);
                else
                    element.sendKeys("1");
                return isClassPresent(element, validClassName);
            } else {
                return false;
            }
        } catch (InvalidElementStateException e) {
            System.out.println("This Field have Thrown an Exception: " + element.getAttribute("id"));
            e.printStackTrace();
            return true;
        } catch (NoSuchElementException | TimeoutException e) {
            System.out.println("A Handled Exception is Thrown" + element);
            e.printStackTrace();
            return false;
        }
    }

}
