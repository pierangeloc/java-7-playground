package com.pierangeloc.foundation.ocp.strings;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.*;

/**
 * Created by pierangeloc on 22-11-14.
 */
public class DateCalendarPlayground {

    public static void dateAndCalendar() {
        System.out.println("--- Date and Calendar ---");
        Date date = new Date(0);
        System.out.println(date);

        date.setTime(date.getTime() + 1_000_000_000_000L);
        System.out.println("date + 1.000.000.000.000 ms " + date);

        Calendar calendar = Calendar.getInstance();
        System.out.println("it's not worth printing a calendar: \n" + calendar);

        calendar.setTime(date);
        Calendar italianCalendar = Calendar.getInstance(new Locale("it", "IT"));
        Calendar newZelandCalendar = Calendar.getInstance(new Locale("en", "NZ"));
        System.out.println("italian calendar first day of week:" + italianCalendar.getFirstDayOfWeek());
        System.out.println("nz calendar first day of week:" + newZelandCalendar.getFirstDayOfWeek());
        italianCalendar.setTime(date);
        newZelandCalendar.setTime(date);
        System.out.println("the 2 dates will be the same, they are the same instant in time");
        System.out.println("italian calendar date: " + italianCalendar.getTime());
        System.out.println("nz calendar date:      " + newZelandCalendar.getTime());

        //add 50 days to the italian calendar
        italianCalendar.add(Calendar.DAY_OF_MONTH, 50);
        //add 2 months to the nz calendar
        newZelandCalendar.add(Calendar.MONTH, 2);
        System.out.println(italianCalendar.getTime());
        System.out.println(newZelandCalendar.getTime());
        System.out.println("let's roll 500 minutes on every time. This should result in adding only 20 mins");
        italianCalendar.roll(Calendar.MINUTE, 500);
        newZelandCalendar.roll(Calendar.MINUTE, 500);
        System.out.println(italianCalendar.getTime());
        System.out.println(newZelandCalendar.getTime());
    }

    public static void formatDate() {
        Date date = new Date(1_000_000_000_000L);
        System.out.println("date: " + date);
        //represent this date in many different formats:
        DateFormat[] dateFormats = new DateFormat[6];
        dateFormats[0] = DateFormat.getInstance();
        dateFormats[1] = DateFormat.getDateInstance();
        dateFormats[2] = DateFormat.getDateInstance(DateFormat.SHORT);
        dateFormats[3] = DateFormat.getDateInstance(DateFormat.MEDIUM);
        dateFormats[4] = DateFormat.getDateInstance(DateFormat.LONG);
        dateFormats[5] = DateFormat.getDateInstance(DateFormat.FULL);

        System.out.println("date in different formats");
        for(DateFormat df : dateFormats) {
            System.out.println(df.format(date));
        }

        //use italian locale:
        DateFormat italianDateFormat = DateFormat.getDateInstance(DateFormat.LONG, new Locale("it", "IT"));
        DateFormat usDateFormat = DateFormat.getDateInstance(DateFormat.LONG, new Locale("en", "US"));
        System.out.println("italian:  "  + italianDateFormat.format(date));
        System.out.println("american: "  + usDateFormat.format(date));
        String italianDate = "19 luglio                 1992";
        System.out.println("parse an italian date: " + italianDate);
        try {
            Date viaDamelio = italianDateFormat.parse(italianDate);
            System.out.println(viaDamelio);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public static void locales() {
        Locale locAR = new Locale("es", "AR");
        Locale locNO = new Locale("ja", "JP");
        Locale locIT = new Locale("it", "IT");

        List<Locale> locales = Arrays.asList(locAR, locNO, locIT);
        System.out.println("locales: " + locales);

        for(Locale firstLocale : locales) {
            System.out.println("locale: " + firstLocale + "; displayName: " + firstLocale.getDisplayName()
                                                        + ", displayCountry: " + firstLocale.getDisplayCountry()
                                                        + ", displayLanguage: " + firstLocale.getDisplayLanguage());
            for(Locale secondLocale : locales) {
                if(!secondLocale.equals(firstLocale)) {
                    System.out.println("locale: " + firstLocale + " interpreted in locale " + secondLocale +  "; displayName: " + firstLocale.getDisplayName(secondLocale)
                            + ", displayCountry: " + firstLocale.getDisplayCountry(secondLocale)
                            + ", displayLanguage: " + firstLocale.getDisplayLanguage(secondLocale));

                }
            }
            System.out.println("");
        }
    }

    public static void numberFormat() {
        Locale locAR = new Locale("es", "AR");
        Locale locNO = new Locale("ja", "JP");
        Locale locIT = new Locale("it", "IT");
        List<Locale> locales = Arrays.asList(locAR, locNO, locIT);
        System.out.println("locales: " + locales);

        int price = 14350;
        System.out.println("price: " + price);
        for(Locale locale : locales) {
            NumberFormat nf = NumberFormat.getIntegerInstance(locale);
            NumberFormat currencyNf = NumberFormat.getCurrencyInstance(locale);
            System.out.println("in locale " + locale);
            System.out.println("number formatted: " + nf.format(price));
            System.out.println("currency formatted: " + currencyNf.format(price));

        }


    }


    public static void main(String[] args) {
//        dateAndCalendar();
//        formatDate();
        locales();
        numberFormat();
    }
}
