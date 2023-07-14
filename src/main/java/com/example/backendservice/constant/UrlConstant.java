package com.example.backendservice.constant;

public class UrlConstant {

  public static class Auth {
    private static final String PRE_FIX = "/auth";

    public static final String LOGIN = PRE_FIX + "/login";
    public static final String REGISTER = PRE_FIX + "/register";
    public static final String LOGOUT = PRE_FIX + "/logout";
    public static final String RESET_PASSWORD = PRE_FIX + "/reset-password";

    private Auth() {
    }
  }

  public static class User {
    private static final String PRE_FIX = "/user";

    public static final String GET_USERS = PRE_FIX;
    public static final String GET_USER = PRE_FIX + "/{userId}";
    public static final String GET_CURRENT_USER = PRE_FIX + "/current";
    public static final String UPDATE_USER = PRE_FIX;
    public static final String CHANGE_PASSWORD = PRE_FIX + "/change-password";

    public static final String GET_FOLLOWERS = PRE_FIX + "/followers";
    public static final String GET_FOLLOWING = PRE_FIX + "/following";

    private User() {
    }
  }

  public static class Follow {
    private static final String PRE_FIX = "/follow";

    public static final String FOLLOW = PRE_FIX;
    public static final String UNFOLLOW = PRE_FIX;

    private Follow() {}
  }

  public static class Post {
    private static final String PRE_FIX = "/post";
    public static final String CREATE_POST = PRE_FIX + "/create_new_post";
    public static final String GET_POST = PRE_FIX + "/{postId}";
    public static final String UPDATE_POST = PRE_FIX + "/{postId}";
    public static final String DELETE_POST = PRE_FIX + "/{postId}";
    public static final String GET_ALL_POST_BY_USER_ID = PRE_FIX + "/get_all_by_user_id/{userId}";
    public static final String GET_ALL_POST_BY_USERNAME = PRE_FIX + "/get_all_by_username/{username}";
    public static final String GET_ALL_POST = PRE_FIX + "/get_all";

    private Post() {
    }
  }

  public static class PostMedia {
    private static final String PRE_FIX = "/postmedia";
    public static final String GET_POSTMEDIA = PRE_FIX + "/{postMediaId}";
    private PostMedia() {
    }
  }

}
