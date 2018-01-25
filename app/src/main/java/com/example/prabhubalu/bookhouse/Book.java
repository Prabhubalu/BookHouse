package com.example.prabhubalu.bookhouse;

public class Book {

    private String title;
    private String author;
    private String image;
    private String description;
    private String phoneNumber;
    private String postedBy;
    private Double price;

    public Book(String title, String author, String image, String description, String phoneNumber, String postedBy, Double price) {
        this.title = title;
        this.author = author;
        this.image = image;
        this.description = description;
        this.phoneNumber = phoneNumber;
        this.postedBy = postedBy;
        this.price = price;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPostedBy() {
        return postedBy;
    }

    public void setPostedBy(String postedBy) {
        this.postedBy = postedBy;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
