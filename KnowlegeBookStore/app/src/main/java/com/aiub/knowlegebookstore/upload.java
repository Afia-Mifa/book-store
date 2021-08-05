package com.aiub.knowlegebookstore;

public class upload {

    private String product_type;
    private  String product_Name;
    private String author;
    private double product_price;
    private int product_quantity;
    private String product_desc;
    private  String Product_Uri;



    public upload() {
    }

    public upload(String product_type, String product_Name, String author, double product_price, int product_quantity, String product_desc, String product_Uri) {
        this.product_type = product_type;
        this.product_Name = product_Name;
        this.author = author;
        this.product_price = product_price;
        this.product_quantity = product_quantity;
        this.product_desc = product_desc;
        Product_Uri = product_Uri;
    }

    public String getProduct_Name() {
        return product_Name;
    }

    public void setProduct_Name(String product_Name) {
        this.product_Name = product_Name;
    }

    public double getProduct_price() {
        return product_price;
    }

    public void setProduct_price(double product_price) {
        this.product_price = product_price;
    }

    public int getProduct_quantity() {
        return product_quantity;
    }

    public void setProduct_quantity(int product_quantity) {
        this.product_quantity = product_quantity;
    }

    public String getProduct_Uri() {
        return Product_Uri;
    }

    public void setProduct_Uri(String product_Uri) {
        Product_Uri = product_Uri;
    }

    public String getProduct_type() {
        return product_type;
    }

    public void setProduct_type(String product_type) {
        this.product_type = product_type;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getProduct_desc() {
        return product_desc;
    }

    public void setProduct_desc(String product_desc) {
        this.product_desc = product_desc;
    }
}
