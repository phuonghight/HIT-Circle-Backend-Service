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
    public static final String GET_FRIENDS = PRE_FIX + "/friends";

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
    public static final String GET_MY_POST = PRE_FIX + "/get_all/{userId}";
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

  public static class Conversation {
    private static final String PRE_FIX = "/conversation";

    public static final String GET_CONVERSATION = PRE_FIX + "/{id}";
    public static final String GET_CONVERSATIONS_OF_ME = PRE_FIX + "/me";
    public static final String GET_CONVERSATION_OF_ME_AND_ONE = PRE_FIX + "/me/{userId}";

    private Conversation() {}
  }

}
