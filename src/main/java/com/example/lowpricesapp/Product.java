package com.example.lowpricesapp;

import java.awt.image.BufferedImage;

public class Product {
    private int id;
    private final String url;
    private final double price;
    private String date;
    private double actualPrice;
    private final String actualDate;
    private final String productName;
    private final String description;
    private final BufferedImage bImage;

    public Product(int id, String url, double price, String date,
                   double actualPrice, String actualDate, String productName,
                   String description, BufferedImage bImage) {
        this.id = id;
        this.url = url;
        this.price = price;
        this.date = date;
        this.actualPrice = actualPrice;
        this.actualDate = actualDate;
        this.productName = productName;
        this.description = description;
        this.bImage = bImage;
    }

    public Product(String[] product, BufferedImage bImage) {
        this.id = Integer.parseInt(product[0]);
        this.url = product[1];
        this.price = Double.parseDouble(product[2]);
        this.date = product[3];
        this.actualPrice = Double.parseDouble(product[4]);
        this.actualDate = product[5];
        this.productName = product[6];
        this.description = product[7];
        this.bImage = bImage;
    }

    public int getId() {
        return id;
    }

    public double getPrice() {
        return price;
    }

    public String getDate() {
        return date;
    }

    public double getActualPrice() {
        return actualPrice;
    }

    public String getActualDate() {
        return actualDate;
    }

    public BufferedImage getbImage() {
        return bImage;
    }

    public String getUrl() {
        return url;
    }

    public String getProductName() {
        return productName;
    }

    public String getDescription() {
        return description;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDate(String actualDate) {
        this.date = actualDate;

    }

    public void setActualPrice(double actualPrice) {
        this.actualPrice = actualPrice;
    }

}
