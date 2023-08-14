package com.ebay.dataproviderobjects;

import com.ebay.utilities.CustomAnnotations;

public class EbaySearchData {

    @CustomAnnotations.ExcelColumn(1)
    String testCaseName;
    @CustomAnnotations.ExcelColumn(2)
    String searchText;
    @CustomAnnotations.ExcelColumn(3)
    String transmission;
    public String getTestCaseName(){
        return testCaseName;
    }
    public String getSearchText(){
        return searchText;
    }
    public String getTransmission(){
        return transmission;
    }


}
