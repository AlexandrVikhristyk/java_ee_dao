package com.myproj.domain;

import com.myproj.dao.Identified;

public class Theme implements Identified<Integer> {
  private Integer id;
  private String theme;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }


  public String getTheme() {
    return theme;
  }

  public void setTheme(String theme) {
    this.theme = theme;
  }

}
