package com.example.shoppingapp.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.shoppingapp.R;
import com.example.shoppingapp.activity.Global.Key;
import com.example.shoppingapp.activity.Global.Link;
import com.example.shoppingapp.activity.adapter.ReviewProductAdapter;
import com.example.shoppingapp.activity.model.ReviewProduct;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ReviewProductActivity extends AppCompatActivity {

    Bundle bundle;
    String id , name_product;

    TextView txt_name_product;

    RequestQueue requestQueue;
    RecyclerView recyclerView_Review_product;
    List<ReviewProduct> listReviewProduct = new ArrayList<>();
    ReviewProductAdapter reviewProductAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_product);

        requestQueue = Volley.newRequestQueue(this);

        bundle = getIntent().getExtras();
        id = bundle.getString(Key.id);
        name_product = bundle.getString(Key.title);

        //Toast.makeText(this, ""+id + "  " + name_product, Toast.LENGTH_SHORT).show();

        txt_name_product = findViewById(R.id.name_product);

        txt_name_product.setText(name_product);

        recyclerView_Review_product = findViewById(R.id.recyclerView_review_product);
        recyclerView_Review_product.setHasFixedSize(true);
        recyclerView_Review_product.setLayoutManager(new LinearLayoutManager(this));
        reviewProductAdapter = new ReviewProductAdapter(this , listReviewProduct);
        recyclerView_Review_product.setAdapter(reviewProductAdapter);

        String url = Link.LINK_REVIEW_PRODUCT;

        Response.Listener<String> listener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Gson gson = new Gson();
                ReviewProduct[] reviewProducts = gson.fromJson(response.toString() ,  ReviewProduct[].class);

                for (int i = 0 ; i<reviewProducts.length ; i++){

                    listReviewProduct.add(reviewProducts[i]);
                    reviewProductAdapter.notifyDataSetChanged();

                }

            }
        };

        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getApplicationContext(), error.getMessage()+"", Toast.LENGTH_SHORT).show();
                Log.d("Error : " , error.getMessage()+"");

            }
        };

        StringRequest request = new StringRequest(Request.Method.POST , url  ,listener , errorListener ){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                HashMap<String , String> map = new HashMap<>();
                map.put(Key.id , id);
                return map;

            }
        };
        requestQueue.add(request);



    }
}