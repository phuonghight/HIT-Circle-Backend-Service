package com.example.backendservice.constant;

public class GenderConstant {
    public static final String MALE = "MALE";
    public static final String FEMALE = "FEMALE";

    public static Boolean isValid(String target) {
        return target.equals(MALE) || target.equals(FEMALE);
    }
}
