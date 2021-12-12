package main.controllers.util;

import java.util.regex.Pattern;

public class PatternDir {

    public static final String BRAND = "^[0-9]{4}[ ]{1}[A-Z]{2}[-]{1}[0-9]{1}$";
    public static final String NAME_PATTERN = "^[А-Я]{1}[а-я]{1,}[\\s][А-Я]{1}[а-я]{1,}[\\s][А-Я]{1}[а-я]{1,}$";
    public static final String COUNTRY = "^[А-Я]{1}[а-я]{1,}$";
    public static final String AREA = "^[А-Я]{1}[а-я]{2,}$";
    public static final String PHONE = "^[+]{1}375[0-9]{9}$";
    public static final String EMAIL_PATTERN = "[a-zA-Z0-9]{1,}[@]{1}[a-z]{3,}[.]{1}+[a-z]{2,}";

    public static boolean check(String pattern, String line){
        return Pattern.matches(pattern, line);
    }

}
