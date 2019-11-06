package com.myproj.domain;

import com.myproj.dao.Identified;

public class Answer implements Identified<Integer> {

  private Integer id;
  private String answer;
  private Question question;


  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }


  public String getAnswer() {
    return answer;
  }

  public void setAnswer(String answer) {
    this.answer = answer;
  }


  public Question getQuestion() {
    return question;
  }

  public void setQuestion(Question question) {
    this.question = question;
  }

}
