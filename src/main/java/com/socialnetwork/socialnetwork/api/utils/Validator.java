package com.socialnetwork.socialnetwork.api.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;
import java.util.regex.Pattern;

public class Validator {
    private DateTimeFormatter dateFormatter;
    public static Boolean emailValidation(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";

        Pattern pat = Pattern.compile(emailRegex);
        if (email == null)
            return false;
        return pat.matcher(email).matches();
    }

    public static Boolean phoneValidation(String phone){
        return false;
    }

    public static Boolean dateValidation(String dateStr){
        try {
            System.out.println(dateStr);
            LocalDate.parse(dateStr,
                    DateTimeFormatter.ofPattern("yyyy-MM-dd")
                            .withResolverStyle(ResolverStyle.STRICT)
            );
            return true;

        } catch (Exception e) {
            return false;
        }

    }
}
