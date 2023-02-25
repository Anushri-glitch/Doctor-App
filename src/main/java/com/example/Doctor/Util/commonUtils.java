package com.example.Doctor.Util;
import java.util.regex.*;
public class commonUtils {
    public static boolean validatePhoneNumber(String phoneNumber){
        Pattern p = Pattern.compile("^\\d{10}$");

        // Pattern class contains matcher() method
        // to find matching between given number
        // and regular expression for which
        // object of Matcher class is created
        Matcher m = p.matcher(phoneNumber);

        // Returning boolean value
        return (m.matches());
    }
}
