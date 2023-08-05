package com.example.backendservice.constant;

public enum SortByDataConstant implements SortByInterface {

  USER {
    @Override
    public String getSortBy(String sortBy) {
      switch (sortBy) {
        case "email":
          return "email";
        case "fullName":
          return "full_name";
        case "username":
          return "username";
        case "birthday":
          return "birthday";
        case "lastModifiedDate":
          return "last_modified_date";
        default:
          return "created_date";
      }
    }
  },

  Follow {
    @Override
    public String getSortBy(String sortBy) {
      if (sortBy.equals(CommonConstant.EMPTY_STRING))
        return "last_modified_date";
      return "created_date";
    }
  },

  Message {
    @Override
    public String getSortBy(String sortBy) {
      if ("lastModifiedDate".equals(sortBy)) {
        return "last_modified_date";
      }
      return "created_date";
    }
  },

  Comment {
    @Override
    public String getSortBy(String sortBy) {
      if ("lastModifiedDate".equals(sortBy)) {
        return "last_modified_date";
      }
      return "created_date";
    }
  }

};


