package com.myproj.domain;
import com.myproj.dao.Identified;

public class Test implements Identified<Integer> {

  private Integer id;
  private String name;
  private Theme theme;


  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Theme getTheme() {
    return theme;
  }

  public void setTheme(Theme theme) {
    this.theme = theme;
  }
}
