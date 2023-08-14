package com.ebay.tests;

import com.ebay.listeners.TestListener;
import com.ebay.utilities.DriverHandler;

import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeSuite;

import java.net.MalformedURLException;

public class BaseTest extends TestListener {

    public DriverHandler driverHandler;

    // Get Directory of the Upload File
//    public static String windowsPath = System.getProperty("user.dir") + "\\TestImages\\";

    @BeforeSuite
    public void resetTestData() {
        // Reset All Test Data Used
        try {

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setUp() throws InterruptedException, MalformedURLException {
        driverHandler = DriverHandler.getInstance();
        driverHandler.createDriver();
        driverHandler.gotoHomePage();
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        driverHandler.getDriver().quit();
        driverHandler.setDriver(null);
    }
}
