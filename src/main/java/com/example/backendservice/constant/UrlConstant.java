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

    public static final String GET_FOLLOWERS_BY_USER_ID = PRE_FIX + "/{userId}/followers";
    public static final String GET_FOLLOWING_BY_USER_ID = PRE_FIX + "/{userId}/following";

    public static final String GET_FRIENDS = PRE_FIX + "/friends";

    private User() {
    }
  }

  public static class Follow {
    private static final String PRE_FIX = "/follow";

    public static final String FOLLOW = PRE_FIX + "/{userId}";
    public static final String UNFOLLOW = PRE_FIX + "/{userId}";

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
    public static final String GET_ALL_POST_MY_REACTION = PRE_FIX + "/get_all_my_react";
    private Post() {
    }
  }

  public static class PostMedia {
    private static final String PRE_FIX = "/postmedia";
    public static final String GET_POSTMEDIA = PRE_FIX + "/{postMediaId}";
    private PostMedia() {
    }
  }

  public static class Reaction {
    private static final String PRE_FIX = "/reaction";
    public static final String CREATE_UPDATE_REACTION = PRE_FIX;
    public static final String REMOVE_REACTION = PRE_FIX + "/{postId}";
    public static final String GET_MY_REACTION = PRE_FIX + "/me/{postId}";
    public static final String GET_USER_REACTION = PRE_FIX + "/user/{postId}";
    public static final String GET_ALL_REACTION = PRE_FIX + "/all/{postId}";
    public static final String GET_ALL_BY_TYPE_REACTION = PRE_FIX + "/all/type";
    private Reaction() {
    }
  }

  public static class Message {
    private static final String PRE_FIX = "/message";

    public static final String SEND_MESSAGE_TO_OTHER = PRE_FIX;
    public static final String GET_MESSAGES_BY_OTHER_BY_ID = PRE_FIX + "/me/{otherId}";

    private Message() {}
  }

  public static class Comment {
    private static final String PRE_FIX = "/comment";

    public static final String CREATE_COMMENT = PRE_FIX;
    public static final String UPDATE_COMMENT = PRE_FIX + "/{commentId}";
    public static final String DELETE_COMMENT = PRE_FIX + "/{commentId}";
    public static final String GET_BY_COMMENT_ID = PRE_FIX + "/{commentId}";
    public static final String GET_ALL_BY_POST_ID = PRE_FIX + "/all/{postId}";
    public static final String GET_ALL_BY_PARENT_COMMENT_ID = PRE_FIX + "/replies/{parentCommentId}";

    private Comment() {
    }
  }

}
