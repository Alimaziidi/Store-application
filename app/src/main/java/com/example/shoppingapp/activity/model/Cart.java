package com.example.shoppingapp.activity.model;

public class Cart {

    private String cart_id , id_product , name , link_img , price , amount;

    public Cart() {
    }

    public String getCart_id() {
        return cart_id;
    }

    public Cart setCart_id(String cart_id) {
        this.cart_id = cart_id;
        return null;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getId_product() {
        return id_product;
    }

    public void setId_product(String id_product) {
        this.id_product = id_product;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLink_img() {
        return link_img;
    }

    public void setLink_img(String link_img) {
        this.link_img = link_img;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}

