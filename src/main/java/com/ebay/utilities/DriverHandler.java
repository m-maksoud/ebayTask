package com.ebay.utilities;

import io.github.bonigarcia.wdm.OperatingSystem;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.net.MalformedURLException;

public class DriverHandler {
    //ThreadLocal will keep local copy of driver
    private static ThreadLocal<WebDriver> driverThread = new ThreadLocal<WebDriver>();

    // Select the WebDriver based on platform
    private void setWebDriver(Platform platform) {

        switch (platform) {
            case MAC:
                WebDriverManager.chromedriver().operatingSystem(OperatingSystem.MAC).setup();
                break;
            case WINDOWS:
                WebDriverManager.chromedriver().operatingSystem(OperatingSystem.WIN).setup();
                break;
            case LINUX:
                WebDriverManager.chromedriver().operatingSystem(OperatingSystem.LINUX).setup();
                break;
            default:
                System.out.println("WebDriver has not been set! There is a problem!\n");
                break;
        }
    }

    public void createDriver() throws MalformedURLException {
        //System.setProperty("java.awt.headless", "true");
        //System.out.println(java.awt.GraphicsEnvironment.isHeadless());
        setWebDriver(ExtentManager.getCurrentPlatform());
        ChromeOptions options = new ChromeOptions();
        options.addArguments("disable-infobars"); // disabling infobars
        options.addArguments("--disable-extensions"); // disabling extensions
        options.addArguments("--disable-dev-shm-usage"); // overcome limited resource problems
        options.addArguments("--no-sandbox"); // Bypass OS security model
        options.addArguments("--incognito");
        //options.addArguments("--headless");
        //options.addArguments("--window-size=1920,1080");
        options.addArguments("start-maximized"); // open Browser in maximized mode
        options.setCapability("applicationCacheEnabled", false);
        //options.setAcceptInsecureCerts(true);
        // For Testing Locally
        //setDriver(new ChromeDriver(options));
        // For Testing Remotely
        //RemoteWebDriver remoteWebDriver = new RemoteWebDriver(new URL("http://seleniumhub:4444/wd/hub"), options);
        //remoteWebDriver.setFileDetector(new LocalFileDetector());
        setDriver(new ChromeDriver(options));
    }

    public static DriverHandler getInstance() {
        return new DriverHandler();
    }

    public void gotoHomePage() {
        driverThread.get().get(Constants.APPLICATION_HOST);
        //+ "individual-protection#Shield");
    }

    public WebDriver getDriver() {
        return driverThread.get();
    }

    public void setDriver(WebDriver driver) {
        driverThread.set(driver);
    }
}
