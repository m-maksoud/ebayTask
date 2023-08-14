package com.ebay.utilities;

import com.ebay.dataproviderobjects.*;
import com.rits.cloning.Cloner;

import org.testng.annotations.DataProvider;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class DataProviderSource {

    @DataProvider(name = "EbayDataFeed")
    public Iterator<Object> EbayDataFeed(Method method) {
        String testCaseName = method.getName();
        EbaySearchData ebaySearchData = new EbaySearchData();
        return fetchDataFromSheet(ebaySearchData, Constants.DATA_WORKBOOK,
                Constants.DATA_SHEET, testCaseName);
    }

    /***************************Fetch Data From Excel Sheet****************************************/
    public Iterator<Object> fetchDataFromSheet(Object obj, String workBookName, String sheetName, String testCaseName) {
        ExcelUtils config = new ExcelUtils();
        config.setTestDataExcelPath("src/main/resources/testdata/" + workBookName);
        config.setExcelFileSheet(sheetName);
        int row = config.getLastRow();
        List<Object> recordsList = new ArrayList<>();
        Cloner cloner = new Cloner();

        for (int i = 1; i < row; i++) {
            Object tempObj;
            if (config.getCellData(i, 0).contains(testCaseName)) {
                int j = 1;
                Field[] fields = obj.getClass().getDeclaredFields();
                for (Field field : fields) {
                    if (field.isAnnotationPresent(CustomAnnotations.ExcelColumn.class)) {
                        CustomAnnotations.ExcelColumn excelColumn = field.getAnnotation(CustomAnnotations.ExcelColumn.class);
                        field.setAccessible(true); // should work on private fields
                        try {
                            if(excelColumn.value() == j) {
                                field.set(obj, config.getCellData(i, j++));
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
                tempObj = cloner.deepClone(obj);
                recordsList.add(tempObj);
            }
        }
        return recordsList.iterator();
    }

}
