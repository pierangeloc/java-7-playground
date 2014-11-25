package com.pierangeloc.foundation.ocp.strings;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Created by pierangeloc on 24-11-14.
 */
public class ResourceBundlePlayground {

    private static final String TROUSERS = "trousers";
    private static final String SHOES = "shoes";
    private static final String SHIRT = "shirt";

    public static void beginner() {
        try{
            ResourceBundle rb = ResourceBundle.getBundle("CATALOGXXX");
        } catch (Exception e) {
            System.out.println("resource bundle can't be found: " + e);
        }

        ResourceBundle catalogRb = ResourceBundle.getBundle("catalog");
        System.out.println("using default locale bundle: " + catalogRb.getLocale());
        System.out.println(catalogRb.getString(TROUSERS));
        System.out.println(catalogRb.getString(SHOES));
        System.out.println(catalogRb.getString(SHIRT));

        ResourceBundle catalogRbIt = ResourceBundle.getBundle("catalog", Locale.ITALY);
        System.out.println("using italian locale bundle: " + catalogRbIt.getLocale());
        System.out.println(catalogRbIt.getString(TROUSERS));
        System.out.println(catalogRbIt.getString(SHOES));
        System.out.println(catalogRbIt.getString(SHIRT));

        ResourceBundle catalogRbFr = ResourceBundle.getBundle("catalog", Locale.FRANCE);
        System.out.println("using french locale bundle: " + catalogRbFr.getLocale());
        System.out.println(catalogRbFr.getString(TROUSERS));
        System.out.println(catalogRbFr.getString(SHOES));
        System.out.println(catalogRbFr.getString(SHIRT));



    }

    public static void main(String[] args) {
        beginner();
    }
}
