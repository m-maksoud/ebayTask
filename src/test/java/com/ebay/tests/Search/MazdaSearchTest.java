package com.ebay.tests.Search;

import com.ebay.dataproviderobjects.EbaySearchData;
import com.ebay.listeners.TestListener;
import com.ebay.pages.HomePage;
import com.ebay.pages.SearchResultPage;
import com.ebay.utilities.DataProviderSource;
import com.ebay.tests.BaseTest;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.lang.reflect.Method;
import java.net.MalformedURLException;

@Listeners(TestListener.class)
public class MazdaSearchTest extends BaseTest {

    SoftAssert softAssert;
    WebDriver driver;

    HomePage homePage;
    SearchResultPage searchResultPage;


    @BeforeMethod(alwaysRun = true)
    public synchronized void setUp(Method method, Object testData[], ITestContext ctx)
            throws InterruptedException, MalformedURLException {
        ctx.setAttribute(method.getName(), "Mazda mx-5 Search Test");
        super.setUp();
        driver = driverHandler.getDriver();
        softAssert = new SoftAssert();
    }

    @Test(alwaysRun = true , dataProvider = "EbayDataFeed" , dataProviderClass = DataProviderSource.class)
    public void mazdaSearchTest(EbaySearchData data){
        homePage = new HomePage(driver);
        homePage.validateTheCorrectPage();
        searchResultPage = homePage.searchField(data.getSearchText());
        //return the number of results found in terminal
        System.out.println("Number of results found is "+ searchResultPage.findResultsNumber());
        searchResultPage.selectTransmissionType(data.getTransmission());
        //Validating that the appeared search result is what was entered in the search text field
        softAssert.assertTrue(searchResultPage.validateObtainedResults(data.getSearchText()));
        softAssert.assertAll();
    }
}
