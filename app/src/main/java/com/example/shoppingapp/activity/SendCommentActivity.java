package com.example.shoppingapp.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shoppingapp.R;
import com.example.shoppingapp.activity.Global.Key;
import com.example.shoppingapp.activity.Global.MyPrefManager;
import com.example.shoppingapp.activity.api.ApiClient;
import com.example.shoppingapp.activity.api.ApiInterface;
import com.example.shoppingapp.activity.api.Message;
import com.squareup.picasso.Picasso;
import com.willy.ratingbar.BaseRatingBar;
import com.willy.ratingbar.ScaleRatingBar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import saman.zamani.persiandate.PersianDate;
import saman.zamani.persiandate.PersianDateFormat;

public class SendCommentActivity extends AppCompatActivity {

    EditText edt_title , edt_description , edt_positive , edt_negative;
    Button btn_send_Comment;
    String    link_img , name_product;
    ImageView img_product;
    TextView txt_name_product;
    ScaleRatingBar ratingBar;

    Bundle bundle;

    static String rating_for_send , email_user ,date , id  ;

    MyPrefManager myPrefManager;

    ApiInterface request;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_comment);

        request = ApiClient.getApiClient().create(ApiInterface.class);

        bundle = getIntent().getExtras();

        myPrefManager = new MyPrefManager(this);
        email_user = myPrefManager.getUserData().get(MyPrefManager.EMAIL);

        id = bundle.getString(Key.id);
        link_img = bundle.getString(Key.link_img);
        name_product = bundle.getString(Key.title);

       // Toast.makeText(this, ""+id, Toast.LENGTH_SHORT).show();

        txt_name_product = findViewById(R.id.name_product);
        img_product = findViewById(R.id.img_product);

        Picasso.get().load(link_img).into(img_product);
        txt_name_product.setText(name_product);

        edt_description = findViewById(R.id.edt_description);
        edt_title = findViewById(R.id.edt_title);
        edt_positive = findViewById(R.id.edt_positive);
        edt_negative = findViewById(R.id.edt_negative);
        btn_send_Comment = findViewById(R.id.btn_send_comment);
        ratingBar = findViewById(R.id.rating);


        PersianDate pdate = new PersianDate();
        PersianDateFormat pdformater1 = new PersianDateFormat("Y/m/d");
        //pdformater1.format(pdate);//1396/05/20
        date = pdformater1.format(pdate);


    ratingBar.setOnRatingChangeListener(new BaseRatingBar.OnRatingChangeListener() {
        @Override
        public void onRatingChange(BaseRatingBar ratingBar, float rating, boolean fromUser) {
            rating_for_send = String.valueOf(rating);
        }
    });


        btn_send_Comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String title = edt_title.getText().toString();
                String description = edt_description.getText().toString();
                String positive = edt_positive.getText().toString();
                String negative = edt_negative.getText().toString();

                addComment(id , email_user , description ,date , rating_for_send , positive , negative , title );

                //Toast.makeText(SendCommentActivity.this, ""+id+email_user+description+date+
                        //positive+negative+title+rating_for_send, Toast.LENGTH_SHORT).show();

                edt_title.setText("");
                edt_negative.setText("");
                edt_positive.setText("");
                edt_description.setText("");

            }
        });


    }

    private void addComment(String id, String email_user, String description,
                             String date, String rating_for_send, String positive,
                             String negative, String title) {


        Call<Message> messageCall = request.sendComment(id , email_user , description , date
        ,rating_for_send , positive , negative , title);
        
        messageCall.enqueue(new Callback<Message>() {
            @Override
            public void onResponse(Call<Message> call, Response<Message> response) {
                
                if (response.isSuccessful() && response.body().isStatus()){

                    Toast.makeText(SendCommentActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    
                }
                
            }

            @Override
            public void onFailure(Call<Message> call, Throwable t) {


                Toast.makeText(SendCommentActivity.this, t.getMessage()+"", Toast.LENGTH_SHORT).show();
            }
        });
        
        
        
    }
}