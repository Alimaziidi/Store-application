package com.example.shoppingapp.activity.adapter;

import android.content.Context;
import android.content.Intent;
import android.text.SpannableString;
import android.text.style.StrikethroughSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shoppingapp.R;
import com.example.shoppingapp.activity.Global.Key;
import com.example.shoppingapp.activity.ShowDetailProductActivity;
import com.example.shoppingapp.activity.model.AmazingOfferProduct;
import com.example.shoppingapp.activity.model.Product;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.List;


public class DetailProductPopularAdapter extends RecyclerView.Adapter<DetailProductPopularAdapter.MyViewHolder> {


    Context context;
    List<AmazingOfferProduct> data;

    public DetailProductPopularAdapter(Context context, List<AmazingOfferProduct> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.item_popular , parent , false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {

        holder.name_product.setText(data.get(position).getName());
        Picasso.get().load(data.get(position).getLink_img()).into(holder.img_product);

        DecimalFormat decimalFormat = new DecimalFormat("###,###");
        String txt_price_deci = decimalFormat.format(Integer.valueOf(data.get(position).getPrice()));
        String txt_off_price_deci = decimalFormat.format(Integer.valueOf(data.get(position).getOffprice()));
        holder.name_product.setText(data.get(position).getName());
        holder.txt_price_off.setText(txt_off_price_deci + " تومان ");
        holder.value_off.setText(data.get(position).getValue_off() + " % ");

        SpannableString spannableString = new SpannableString(txt_price_deci);
        spannableString.setSpan(new StrikethroughSpan() , 0 , data.get(position).getPrice().length() , SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE);

        holder.txt_price.setText(spannableString);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AmazingOfferProduct amazingOfferProduct=data.get(position);

                Intent intent = new Intent(context, ShowDetailProductActivity.class);

                // Pass necessary information about the clicked product to the intent
                intent.putExtra(Key.id , amazingOfferProduct.getId());
                intent.putExtra(Key.title, amazingOfferProduct.getName());
                intent.putExtra(Key.price, amazingOfferProduct.getPrice());
                intent.putExtra(Key.link_img, amazingOfferProduct.getLink_img());
                intent.putExtra(Key.brand ,amazingOfferProduct.getBrand());
                intent.putExtra(Key.category_id , amazingOfferProduct.getCategory_id());
                intent.putExtra(Key.value_off , amazingOfferProduct.getValue_off());
                intent.putExtra(Key.price_off , amazingOfferProduct.getOffprice());
                intent.putExtra(Key.amount , amazingOfferProduct.getAmount());

                // Add any other information you want to pass

                // Start the ShowDetailProductActivity
                context.startActivity(intent);

            }
        });


    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder  {

        ImageView img_product;
        TextView  name_product , txt_price_off , value_off , txt_price;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            img_product = itemView.findViewById(R.id.img_product);
            name_product = itemView.findViewById(R.id.name_product);
            txt_price_off = itemView.findViewById(R.id.txt_price_off);
            value_off = itemView.findViewById(R.id.value_off);
            txt_price = itemView.findViewById(R.id.txt_price);

        }

    }
}
