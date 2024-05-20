package com.example.shoppingapp.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.shoppingapp.R;
import com.example.shoppingapp.activity.Global.Key;
import com.example.shoppingapp.activity.Global.Link;
import com.example.shoppingapp.activity.Global.MyPrefManager;
import com.example.shoppingapp.activity.adapter.CartAdapter;
import com.example.shoppingapp.activity.api.ApiClient;
import com.example.shoppingapp.activity.api.ApiInterface;
import com.example.shoppingapp.activity.api.Message;
import com.example.shoppingapp.activity.api.UserData;
import com.example.shoppingapp.activity.api.Users;
import com.example.shoppingapp.activity.model.Cart;
import com.example.shoppingapp.activity.model.Product;
import com.google.android.material.bottomsheet.BottomSheetDialog;


import java.util.ArrayList;
import java.util.List;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CartActivity extends AppCompatActivity {


    RecyclerView recyclerView_cart;
    List<Cart> listCart = new ArrayList<>();
    CartAdapter adapter;
    ApiInterface request;
    String email;


    Bundle bundle;


    String id,amount;
    RequestQueue requestQueue;
    MyPrefManager pref;

    String user_email;

    ProgressDialog progressDialog1;

    private int TOTAL_PRICE , ALL_PRODUCT_SIZE;
    Button btn_continue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        pref = new MyPrefManager(this);
        user_email = pref.getUserData().get(MyPrefManager.EMAIL);










        request = ApiClient.getApiClient().create(ApiInterface.class);
        pref = new MyPrefManager(this);
        email = pref.getUserData().get(MyPrefManager.EMAIL);

        recyclerView_cart = findViewById(R.id.recycleRView_cart);
        recyclerView_cart.setHasFixedSize(true);
        recyclerView_cart.setLayoutManager(new LinearLayoutManager(this));

        btn_continue = findViewById(R.id.btn_continue);



        Call<List<Cart>> call = request.getListCart(email);
        call.enqueue(new Callback<List<Cart>>() {
            @Override
            public void onResponse(Call<List<Cart>> call, Response<List<Cart>> response) {

                listCart = response.body();
                adapter = new CartAdapter(getApplicationContext(), listCart, new CartAdapter.deleteProductForId() {
                    @Override
                    public void IItemDeleteProduct(Cart cart) {

                        Call<Message> callDelete = request.deleteCart(cart.getId_product());

                        callDelete.enqueue(new Callback<Message>() {
                            @Override
                            public void onResponse(Call<Message> call, Response<Message> response) {

                                if (response.isSuccessful() && response.body() != null && response.body().getMessage().equals("Ok")){
                                    adapter.deleteProduct(cart);


                                }

                            }

                            @Override
                            public void onFailure(Call<Message> call, Throwable t) {
                                Toast.makeText(CartActivity.this, t.getMessage()+"", Toast.LENGTH_SHORT).show();
                            }
                        });


                    }
                }, new CartAdapter.priceAndSizeProduct() {
                    @Override
                    public void IItemPriceAndSize(String price, String data,int pos) {

                        TOTAL_PRICE+=Integer.parseInt(price);


                        btn_continue.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(CartActivity.this);
                                View layout_bottom_sheet = LayoutInflater.from(getApplicationContext()).inflate(
                                        R.layout.layout_bottom_sheet_cart , findViewById(R.id.parent));


                                TextView txt_all_product_size = layout_bottom_sheet.findViewById(R.id.txt_all_product_size);
                                TextView txt_total_product = layout_bottom_sheet.findViewById(R.id.txt_total_price);
                                Button   btn_buy = layout_bottom_sheet.findViewById(R.id.btn_buy);

                                ALL_PRODUCT_SIZE = Integer.parseInt(data);

                                txt_total_product.setText(TOTAL_PRICE+"");
                                txt_all_product_size.setText(ALL_PRODUCT_SIZE+"");

                                btn_buy.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {

                                        bundle = getIntent().getExtras();

                                        if (bundle != null && bundle.containsKey(Key.amount)) {
                                            id = bundle.getString(Key.id);
                                            amount = bundle.getString(Key.amount);

                                            if ("0".equals(amount)) {
                                                Toast.makeText(CartActivity.this, "موجودی کالا کافی نمی باشد", Toast.LENGTH_SHORT).show();
                                            } else {

                                                Amount(user_email, id);
                                                adapter.deleteProduct(listCart.get(pos));


                                            }
                                        } else {
                                            Toast.makeText(CartActivity.this, " خرید شما انجام نشد.", Toast.LENGTH_SHORT).show();
                                        }


                                    }
                                });


                                bottomSheetDialog.setContentView(layout_bottom_sheet);
                                bottomSheetDialog.show();

                            }
                        });

                    }
                });
                recyclerView_cart.setAdapter(adapter);

            }

            @Override
            public void onFailure(Call<List<Cart>> call, Throwable t) {

                Toast.makeText(CartActivity.this, t.getMessage()+"", Toast.LENGTH_SHORT).show();

            }
        });

    }




    private void Amount(final String Email, String Product) {

        String url="http://192.168.1.100/shopping/amount.php?user_email="+Email+"&id_product="+Product;
        StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {




                Toast.makeText(CartActivity.this, "خرید شما به موفقیت انجام شد", Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(CartActivity.this,HomeActivity.class);
                startActivity(intent);








            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(CartActivity.this, "خرید شما به  ناموفق انجام شد", Toast.LENGTH_SHORT).show();


            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);









    }










}