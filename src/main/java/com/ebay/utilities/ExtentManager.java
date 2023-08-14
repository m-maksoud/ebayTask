package com.ebay.utilities;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentKlovReporter;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.ebay.utilities.Constants;
import org.openqa.selenium.Platform;

import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ExtentManager
{
    private static ExtentReports extent;
    private static Platform platform;
    public static String reportFileName =
            "Ebay Task" + "_" + new SimpleDateFormat("dd-MM-yyyy hh-mm-ss-ms").format(new Date())
                    + ".html";
    private static String macPath = System.getProperty("user.dir") + "/TestReport";
    private static String linuxPath = System.getProperty("user.dir") + "/TestReport";
    private static String windowsPath = System.getProperty("user.dir") + "\\TestReport";
    private static String macReportFileLoc = macPath + "/" + reportFileName;
    private static String linuxReportFileLoc = linuxPath + "/" + reportFileName;
    private static String winReportFileLoc = windowsPath + "\\" + reportFileName;

    public static String filePathAndName;

    public static ExtentReports getInstance()
    {
        if (extent == null)
        {
            createInstance();
        }
        return extent;
    }

    // Create an extent report instance
    public static ExtentReports createInstance()
    {
        platform = getCurrentPlatform();
        filePathAndName = getReportFileLocation(platform);
        ExtentSparkReporter htmlReporter = new ExtentSparkReporter(filePathAndName);
        //		htmlReporter.config().setTestViewChartLocation(ChartLocation.BOTTOM);
        //		htmlReporter.config().setChartVisibilityOnOpen(true);
        htmlReporter.config().setTheme(Theme.DARK);
        htmlReporter.config().setDocumentTitle("Ebay Task");
        htmlReporter.config().setEncoding("utf-8");
        htmlReporter.config().setReportName("Ebay Task Report on " + Constants.ENVIRONMENT_NAME + " Environment");
        htmlReporter.config().setTimelineEnabled(true);

        extent = new ExtentReports();

        if (Constants.REMOTE_SERVER.equals("true")) {
            ExtentKlovReporter klov = new ExtentKlovReporter("Ebay Task");
            try {
                klov.loadInitializationParams(
                        new FileInputStream(System.getProperty("user.dir") + "/src/main/resources/klov.properties"));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            System.out.println("Klov is Activated!");

            // For Testing on Server
            extent.attachReporter(htmlReporter, klov);
        }
        else {
            // For Testing Locally
            extent.attachReporter(htmlReporter);
        }

        extent.setSystemInfo("OS", getCurrentPlatform().toString());


        return extent;
    }
    // Select the extent report file location based on platform
    private static String getReportFileLocation(Platform platform)
    {
        String reportFileLocation = null;
        switch (platform)
        {
            case MAC:
                reportFileLocation = macReportFileLoc;
                createReportPath(macPath);
                System.out.println("ExtentReport Path for MAC: " + macPath + "\n");
                break;
            case WINDOWS:
                reportFileLocation = winReportFileLoc;
                createReportPath(windowsPath);
                System.out.println("ExtentReport Path for WINDOWS: " + windowsPath + "\n");
                break;
            case LINUX:
                reportFileLocation = linuxReportFileLoc;
                createReportPath(linuxPath);
                System.out.println("ExtentReport Path for LINUX: " + linuxPath + "\n");
                break;
            default:
                System.out.println("ExtentReport path has not been set! There is a problem!\n");
                break;
        }
        return reportFileLocation;
    }
    // Create the report path if it does not exist
    private static void createReportPath(String path)
    {
        File testDirectory = new File(path);
        if (!testDirectory.exists())
        {
            if (testDirectory.mkdir())
            {
                System.out.println("Directory: " + path + " is created!");
            }
            else
            {
                System.out.println("Failed to create directory: " + path);
            }
        }
        else
        {
            System.out.println("Directory already exists: " + path);
        }
    }
    // Get current platform
    public static Platform getCurrentPlatform()
    {
        if (platform == null)
        {
            String operSys = System.getProperty("os.name").toLowerCase();
            if (operSys.contains("win"))
            {
                platform = Platform.WINDOWS;
            }
            else if (operSys.contains("nix") || operSys.contains("nux") || operSys.contains("aix"))
            {
                platform = Platform.LINUX;
            }
            else if (operSys.contains("mac"))
            {
                platform = Platform.MAC;
            }
        }
        return platform;
    }
    public static void openReport(){
        try {
            Desktop.getDesktop().browse(new File(getReportFileLocation(Platform.WINDOWS)).toURI());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

