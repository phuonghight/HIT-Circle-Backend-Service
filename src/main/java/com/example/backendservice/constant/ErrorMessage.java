package com.example.backendservice.constant;

public class ErrorMessage {

  public static final String ERR_EXCEPTION_GENERAL = "exception.general";
  public static final String UNAUTHORIZED = "exception.unauthorized";
  public static final String FORBIDDEN = "exception.forbidden";
  public static final String FORBIDDEN_UPDATE_DELETE = "exception.forbidden.update-delete";

  //error validation dto
  public static final String INVALID_SOME_THING_FIELD = "invalid.general";
  public static final String INVALID_FORMAT_SOME_THING_FIELD = "invalid.general.format";
  public static final String INVALID_SOME_THING_FIELD_IS_REQUIRED = "invalid.general.required";
  public static final String NOT_BLANK_FIELD = "invalid.general.not-blank";
  public static final String INVALID_FORMAT_PASSWORD = "invalid.password-format";
  public static final String INVALID_FORMAT_EMAIL = "invalid.email-format";
  public static final String INVALID_FORMAT_PHONE = "invalid.phone-format";
  public static final String INVALID_DATE = "invalid.date-format";
  public static final String INVALID_DATE_FEATURE = "invalid.date-future";
  public static final String INVALID_DATETIME = "invalid.datetime-format";

  public static class Auth {
    public static final String ERR_INCORRECT_ACCOUNT = "exception.auth.incorrect.account";
    public static final String ERR_INCORRECT_PASSWORD = "exception.auth.incorrect.password";
    public static final String ERR_ACCOUNT_NOT_ENABLED = "exception.auth.account.not.enabled";
    public static final String ERR_ACCOUNT_LOCKED = "exception.auth.account.locked";
    public static final String INVALID_REFRESH_TOKEN = "exception.auth.invalid.refresh.token";
    public static final String EXPIRED_REFRESH_TOKEN = "exception.auth.expired.refresh.token";
  }

  public static class User {
    public static final String ERR_NOT_FOUND_USERNAME = "exception.user.not.found.username";
    public static final String ERR_NOT_FOUND_ID = "exception.user.not.found.id";
    public static final String ERR_ALREADY_EXIST_USER = "exception.user.already.exist";
  }

  public static class Follow {
    public static final String ERR_NOT_FOUND = "exception.follow.not.found";
    public static final String ERR_ALREADY_EXIST = "exception.follow.already.exist";
  }

  public static class Post {
    public static final String ERR_NOT_FOUND_ID = "exception.post.not.found.id";
    public static final String ERR_INVALID = "exception.post.invalid";
    public static final String ERR_UNAUTHORIZED = "exception.post.unauthorized";
  }

  public static class PostMedia {
    public static final String ERR_NOT_FOUND_ID = "exception.postmedia.not.found.id";
  }
}
