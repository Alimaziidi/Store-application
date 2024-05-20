package com.example.shoppingapp.activity.adapter;

import android.content.Context;
import android.content.Intent;
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
import com.example.shoppingapp.activity.model.Product;
import com.squareup.picasso.Picasso;

import java.util.List;


public class SimilarProductAdapter extends RecyclerView.Adapter<SimilarProductAdapter.MyViewHolder> {

    Context context;
    List<Product> data;

    public SimilarProductAdapter(Context context, List<Product> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.item_similar_product , parent , false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.txt_name_product.setText(data.get(position).getName());
        holder.txt_price.setText(data.get(position).getPrice());
        Picasso.get().load(data.get(position).getLink_img()).into(holder.img_product);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Product clickedProduct = data.get(position);

                // Create an Intent to start ShowDetailProductActivity
                Intent intent = new Intent(context, ShowDetailProductActivity.class);

                // Pass necessary information about the clicked product to the intent
                intent.putExtra(Key.id , clickedProduct.getId());
                intent.putExtra(Key.title, clickedProduct.getName());
                intent.putExtra(Key.price, clickedProduct.getPrice());
                intent.putExtra(Key.link_img, clickedProduct.getLink_img());
                intent.putExtra(Key.brand ,clickedProduct.getBrand());
                intent.putExtra(Key.category_id , clickedProduct.getCategory_id());
                intent.putExtra(Key.value_off , clickedProduct.getValue_off());
                intent.putExtra(Key.price_off , clickedProduct.getOffprice());
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

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView img_product;
        TextView txt_name_product , txt_price;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            img_product = itemView.findViewById(R.id.img_product);
            txt_name_product = itemView.findViewById(R.id.name_product);
            txt_price = itemView.findViewById(R.id.txt_price);

        }
    }
}
