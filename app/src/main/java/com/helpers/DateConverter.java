package com.helpers;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateConverter {
    public static String formatDate(String dateOnline, String pattern, String returnPattern){
        String lastOnline = dateOnline;
        try {
            if(!dateOnline.equals("N/A")) {
                Date date  = new SimpleDateFormat(pattern).parse(dateOnline);
                lastOnline = new SimpleDateFormat(returnPattern).format(date);
            }
        }catch (Exception exc){
            exc.printStackTrace();
        }
        return lastOnline;
    }
}
