package com.example.furniture;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Cart view
 * showing the name, price and number of products in each
 */

public class CartViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView txtProductName,txtProductPrice,txtProductQuantity;

    private ItemClickListner itemClickListner;

    public CartViewHolder(@NonNull View itemView) {
        super(itemView);

        txtProductName=itemView.findViewById(R.id.cartname);
        txtProductPrice=itemView.findViewById(R.id.cartprice);
        txtProductQuantity=itemView.findViewById(R.id.cartquantity);
    }

    /**
     * listener for cart
     * @param v
     */
    @Override
    public void onClick(View v) {
itemClickListner.onClick(v,getAdapterPosition(),false);
    }

    public void setItemClickListner(ItemClickListner itemClickListner){
        this.itemClickListner=itemClickListner;
    }
}
