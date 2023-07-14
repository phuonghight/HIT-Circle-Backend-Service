package com.example.backendservice.constant;

import java.util.List;

public class ReactionConstant {
    public static final List<String> REACTION = List.of("Like", "Love", "Haha","Sad", "Wow", "Angry");

    public static Boolean isValid(String target) {
        return REACTION.contains(target);
    }
}
