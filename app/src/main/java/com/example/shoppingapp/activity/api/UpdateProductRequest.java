package com.example.shoppingapp.activity.api;

import com.google.gson.annotations.SerializedName;

class UpdateProductRequest {

    @SerializedName("user_email")
    private String userEmail;

    @SerializedName("id_product")
    private String idProduct;

    public UpdateProductRequest(String userEmail, String idProduct) {
        this.userEmail = userEmail;
        this.idProduct = idProduct;
    }
}
