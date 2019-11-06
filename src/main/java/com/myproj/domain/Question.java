package com.myproj.domain;

import com.myproj.dao.Identified;

public class Question implements Identified<Integer> {

  private Integer id;
  private String question;
  private Test test;


  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }


  public String getQuestion() {
    return question;
  }

  public void setQuestion(String question) {
    this.question = question;
  }


  public Test getTest() {
    return test;
  }

  public void setTest(Test test) {
    this.test = test;
  }

}
