package com.example.backendservice.constant;

public enum SortByDataConstant implements SortByInterface {

  USER {
    @Override
    public String getSortBy(String sortBy) {
      if (sortBy.equals(CommonConstant.EMPTY_STRING))
        return "createdDate";
      return sortBy;
    }
  },

  Follow {
    @Override
    public String getSortBy(String sortBy) {
      if (sortBy.equals(CommonConstant.EMPTY_STRING))
        return "createdDate";
      return sortBy;
    }
  },

  Conversation {
    @Override
    public String getSortBy(String sortBy) {
      if ("createdDate".equals(sortBy)) {
        return "created_date";
      }
      return "last_modified_date";
    }
  }

}
