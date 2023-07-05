package com.example.backendservice.constant;

public class UrlConstant {

  public static class Auth {
    private static final String PRE_FIX = "/auth";

    public static final String LOGIN = PRE_FIX + "/login";
    public static final String REGISTER = PRE_FIX + "/register";
    public static final String LOGOUT = PRE_FIX + "/logout";

    private Auth() {
    }
  }

  public static class User {
    private static final String PRE_FIX = "/user";

    public static final String GET_USERS = PRE_FIX;
    public static final String GET_USER = PRE_FIX + "/{userId}";
    public static final String GET_CURRENT_USER = PRE_FIX + "/current";
    public static final String UPDATE_USER = PRE_FIX;

    private User() {
    }
  }

  public static class Follow {
    private static final String PRE_FIX = "/follow";

    public static final String FOLLOW = PRE_FIX;
    public static final String UNFOLLOW = PRE_FIX;

    private Follow() {}
  }

}
