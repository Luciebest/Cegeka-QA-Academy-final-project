package com.cegeka.academy.qa.tests.steps;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("cucumber-glue")
public class BookItem {
    private String isbn;
    private String title;
    private String subtitle;
    private String author;
    private String publisher;
    private int totalPages;
    private String description;
    private String website;

    public BookItem(String title, String author, String publisher) {
    this.title = title;
    this.author = author;
    this.publisher = publisher;
    }

    public BookItem() {
        this.isbn = "";
        this.title = "";
        this.subtitle = "";
        this.author = "";
        this.publisher = "";
        this.totalPages = 0;
        this.description = "";
        this.website = "";
    }
    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }
    @Override
    public String toString() {
        return "Book{" +
                "title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", publisher='" + publisher + '\'' +
                '}';
    }
    // The print statement in the test method "checkInventoryBooks()"
    // should now output a list of Book objects in a more readable format,
    // since we have overridden the toString() method in the Book class


}