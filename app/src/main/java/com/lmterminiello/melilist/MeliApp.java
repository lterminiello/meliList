package com.lmterminiello.melilist;


import android.app.Application;


import com.facebook.stetho.Stetho;


public class MeliApp extends Application{

    private static final String SITE = "MLA";
    private static final String ARG = "ARS";
    private static final String CURRENCY_ARG = "$";
    private static final String CURRENCY_DEFAULT = "USD";

    @Override
    public void onCreate() {
        super.onCreate();
        Stetho.initializeWithDefaults(this);

    }

    public static String getCurrentSite() {
        return SITE;
    }

    public static String getCurrency(String country) {
        return ARG.equals(country) ? CURRENCY_ARG : CURRENCY_DEFAULT;
    }

}
