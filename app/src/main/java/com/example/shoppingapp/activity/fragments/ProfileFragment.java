package com.example.shoppingapp.activity.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shoppingapp.R;
import com.example.shoppingapp.activity.CartActivity;
import com.example.shoppingapp.activity.FavoriteActivity;
import com.example.shoppingapp.activity.Global.MyPrefManager;
import com.example.shoppingapp.activity.LoginActivity;
import com.example.shoppingapp.activity.OrderProductActivity;
import com.example.shoppingapp.activity.QuestionActivity;


public class ProfileFragment extends Fragment {



    public ProfileFragment() {
        // Required empty public constructor
    }

    View view;
    TextView txt_phone , txt_email;

    MyPrefManager myPrefManager;

    LinearLayout layout_cart;

    CardView card_question , card_favorite , card_order;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_profile, container, false);

        myPrefManager = new MyPrefManager(getContext());

        txt_phone = view.findViewById(R.id.txt_phone);
        txt_email = view.findViewById(R.id.txt_email);

        layout_cart=view.findViewById(R.id.cart);

        card_question = view.findViewById(R.id.card_question);
        card_favorite = view.findViewById(R.id.card_favorite);

        card_order = view.findViewById(R.id.order_card);

        txt_phone.setText("موبایل : "+myPrefManager.getUserData().get(MyPrefManager.PHONE));
        txt_email.setText("ایمیل : "+myPrefManager.getUserData().get(MyPrefManager.EMAIL));


        card_question.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(getContext() , QuestionActivity.class));

            }
        });

        layout_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (myPrefManager.getUserData().get(MyPrefManager.PHONE)==null){

                    Toast.makeText(getContext(), "جهت مشاهده سبد خرید لاگین یا ثبت نام انجام دهید.", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getContext() , LoginActivity.class));



                }else {
                    startActivity(new Intent(getContext() , CartActivity.class));

                }
            }
        });

        card_favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(getContext() , FavoriteActivity.class));

            }
        });

        card_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(getContext() , OrderProductActivity.class));

            }
        });

        return view;

    }
}