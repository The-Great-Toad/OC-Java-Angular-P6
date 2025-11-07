package com.openclassrooms.mdd.constant;

public class Regex {
    public static final String PASSWORD_PATTERN =
        "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!]).*$";

    private Regex() {}
}
