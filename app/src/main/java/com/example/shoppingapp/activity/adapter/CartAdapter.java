package com.example.shoppingapp.activity.adapter;

import android.annotation.SuppressLint;
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
import com.example.shoppingapp.activity.model.Cart;
import com.squareup.picasso.Picasso;

import java.util.List;


public class CartAdapter extends RecyclerView.Adapter<CartAdapter.MyViewHolder> {

    Context context;
    List<Cart> data;
    deleteProductForId deleteProduct;
    priceAndSizeProduct priceAndSizeProduct;

    public CartAdapter(Context context, List<Cart> data, deleteProductForId deleteProduct, CartAdapter.priceAndSizeProduct priceAndSizeProduct) {
        this.context = context;
        this.data = data;
        this.deleteProduct = deleteProduct;
        this.priceAndSizeProduct = priceAndSizeProduct;
    }

    public CartAdapter(Context context, List<Cart> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.item_cart , parent , false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {

        holder.txt_name_product.setText(data.get(position).getName());
        holder.txt_price.setText(data.get(position).getPrice());
        holder.text_amount.setText(" موجودی کالا : "+data.get(position).getAmount());
        Picasso.get().load(data.get(position).getLink_img()).into(holder.img_product);

        holder.txt_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                deleteProduct.IItemDeleteProduct(data.get(position));



            }
        });

        priceAndSizeProduct.IItemPriceAndSize(data.get(position).getPrice() , data.size()+"",position);

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView img_product;
        TextView txt_name_product , txt_price , txt_delete,text_amount;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            img_product = itemView.findViewById(R.id.img_product);
            txt_name_product = itemView.findViewById(R.id.name_product);
            txt_price = itemView.findViewById(R.id.txt_price);
            txt_delete = itemView.findViewById(R.id.txt_delete);
            text_amount=itemView.findViewById(R.id.text_amount);


        }
    }

    public interface deleteProductForId {
        void IItemDeleteProduct(Cart cart);

    }

    public interface priceAndSizeProduct{
        void IItemPriceAndSize(String price , String  data,int pos);
    }

    public void deleteProduct(Cart cart){

        int index = data.indexOf(cart);
        data.remove(index);
        notifyDataSetChanged();

    }
    public void updateProductAmount(int position, String amount) {

        Cart cart = data.get(position);
        cart.setAmount(amount);
        notifyItemChanged(position);
    }

    private OnBuyClickListener onBuyClickListener;

    // Constructor and other methods...

    public void setOnBuyClickListener(Cart cart) {
        int index = data.indexOf(cart);
        data.remove(index);
        notifyDataSetChanged();
    }

    public interface OnBuyClickListener {
        void onBuyClick(Cart cart);
    }


}
