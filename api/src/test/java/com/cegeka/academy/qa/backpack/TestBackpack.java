package com.cegeka.academy.qa.backpack;

import com.cegeka.academy.qa.json.model.Book;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class TestBackpack {
  private String userId;
  private String token;
  private String userName;
  private String password;
  private List<String> isbns = new ArrayList<>();
  private Book book;

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }
  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }
  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }
  public List<String> getIsbns() {
    return isbns;
  }

  public void setIsbns(List<String> isbns) {
    this.isbns = isbns;
  }

  public void addIsbn(String isbn) {
    isbns.add(isbn);
  }
  public Book getBook() {
    return book;
  }

  public void setBook(Book book) {
    this.book = book;
  }
}


