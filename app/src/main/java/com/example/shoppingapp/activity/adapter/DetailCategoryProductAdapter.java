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
import com.example.shoppingapp.activity.model.Product;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.List;


public class DetailCategoryProductAdapter extends RecyclerView.Adapter<DetailCategoryProductAdapter.MyViewHolder> {


    Context context;
    List<Product> data;

    public DetailCategoryProductAdapter(Context context, List<Product> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.item_detail_product , parent , false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {

        DecimalFormat decimalFormat = new DecimalFormat("###,###");
        String txt_price_deci = decimalFormat.format(Integer.valueOf(data.get(position).getPrice()));
        //String txt_off_price_deci = decimalFormat.format(Integer.valueOf(amazingOfferProduct.getOffprice()));
        holder.name_product.setText(data.get(position).getName());
        //txt_price_off.setText(txt_off_price_deci + " تومان ");
        //value_off.setText(amazingOfferProduct.getValue_off() + " % ");
        holder.txt_price.setText(txt_price_deci);

        Picasso.get().load(data.get(position).getLink_img()).into(holder.img_product);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Product product=data.get(position);

                Intent intent = new Intent(context, ShowDetailProductActivity.class);

                // Pass necessary information about the clicked product to the intent
                intent.putExtra(Key.id , product.getId());
                intent.putExtra(Key.title, product.getName());
                intent.putExtra(Key.price, product.getPrice());
                intent.putExtra(Key.link_img, product.getLink_img());
                intent.putExtra(Key.brand ,product.getBrand());
                intent.putExtra(Key.category_id , product.getCategory_id());
                intent.putExtra(Key.value_off , product.getValue_off());
                intent.putExtra(Key.price_off , product.getOffprice());
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
        TextView name_product , txt_price_off , value_off , txt_price;

        public MyViewHolder(@NonNull View itemView) {

            super(itemView);

            img_product = itemView.findViewById(R.id.img_product);
            name_product = itemView.findViewById(R.id.name_product);
            //txt_price_off = itemView.findViewById(R.id.txt_price_off);
            //value_off = itemView.findViewById(R.id.value_off);
            txt_price = itemView.findViewById(R.id.txt_price);



        }

    }
}
