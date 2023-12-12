package com.socialnetwork.socialnetwork.api.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

public class Converter {


    public static Date stringToDate(String strDate) throws ParseException {
        if (strDate == null) {
            return null;
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date date = dateFormat.parse(strDate);
        System.out.println("zzzzzzzzzzzzzzzzzzzzzzzz"+date.getClass().getName());
        return date;
    }
}
