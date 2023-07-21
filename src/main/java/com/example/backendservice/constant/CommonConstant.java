package com.example.backendservice.constant;

public class CommonConstant {

  public static final String APP_NAME = "Hit Circle Application";

  public static final String SORT_TYPE_ASC = "ASC";
  public static final String SORT_TYPE_DESC = "DESC";
  public static final Integer PAGE_SIZE_DEFAULT = 10;

  public static final Integer RANDOM_PASSWORD_LENGTH = 8;
  public static final Integer ZERO_INT_VALUE = 0;
  public static final Integer ONE_INT_VALUE = 1;
  public static final Long ZERO_VALUE = 0L;
  public static final Long ONE_VALUE = 1L;

  public static final String EMPTY_STRING = "";
  public static final String BEARER_TOKEN = "Bearer";

  public static final String PATTERN_DATE_TIME = "yyyy-MM-dd HH:mm:ss";
  public static final String PATTERN_DATE = "yyyy-MM-dd";

  public static final String CONTENT_TYPE_DOCUMENT = "txt doc pdf ppt pps xlsx xls docx";
  public static final String CONTENT_TYPE_IMAGE = "png jpg jpeg webp gif";
  public static final String CONTENT_TYPE_VIDEO = "mp4 x-msvideo x-flv x-ms-wmv quicktime x-matroska mpeg webm h264 hevc x-m4v";
  public static final Integer MAX_NUMBER_IMAGE = 5;
  public static final Integer MAX_NUMBER_VIDEO = 1;
  public static final Long MAX_IMAGE_SIZE_MB = 5L;
  public static final Long MAX_VIDEO_SIZE_MB = 20L;
  public static final Long MAX_IMAGE_SIZE_BYTES = MAX_IMAGE_SIZE_MB * 1024 * 1024;
  public static final Long MAX_VIDEO_SIZE_BYTES = MAX_VIDEO_SIZE_MB * 1024 * 1024;

  public static final Integer NUM_OF_MESSAGES_PER_PAGE_DEFAULT = 30;
  public static final Integer MAX_OF_LEVEL_COMMENT= 3;
  public static final Integer NUM_OF_COMMENT_PER_PAGE_DEFAULT = 20;

  public static class Event {
    //Client
    public static final String CLIENT_SEND_MESSAGE = "client_send_message";
    public static final String CLIENT_JOIN_ROOM = "client_join_room";

    //Server
    public static final String SERVER_SEND_ERROR = "server_send_error";
    public static final String SERVER_SEND_MESSAGE = "server_send_message";
  }

  public static class Key {
    public static final String USER_ID = "userId";
    public static final String ACCESS_TOKEN = "accessToken";
  }

}
