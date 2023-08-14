package com.ebay.utilities;

import java.util.ResourceBundle;

public class Constants {

    private static final ResourceBundle ENVIRONMENT_RN = ResourceBundle.getBundle("environment");
    public static final String ENVIRONMENT_NAME = ENVIRONMENT_RN.getString("env.name");
    public static final String APPLICATION_HOST = ENVIRONMENT_RN.getString("app.host");
    public static final String REMOTE_SERVER = ENVIRONMENT_RN.getString("remote.server");


    /***********Csr Pages Data***********/
    public static final String DATA_WORKBOOK = "Data.xlsx";
    public static final String DATA_SHEET = "Login";
}
