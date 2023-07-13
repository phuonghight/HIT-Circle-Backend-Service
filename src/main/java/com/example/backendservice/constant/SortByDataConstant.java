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

  Message {
    @Override
    public String getSortBy(String sortBy) {
      if ("lastModifiedDate".equals(sortBy)) {
        return "last_modified_date";
      }
      return "created_date";
    }
  }

}
