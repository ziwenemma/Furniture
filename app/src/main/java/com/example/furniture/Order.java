package com.example.furniture;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


public class Order extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView txtProductName,txtProductPrice,txtProductQuantity,txtProductTime,txtProductAmount;

    private ItemClickListner itemClickListner;

    public Order(@NonNull View itemView) {
        super(itemView);

        txtProductName=itemView.findViewById(R.id.ordername);
        txtProductPrice=itemView.findViewById(R.id.orderprice);
        txtProductQuantity=itemView.findViewById(R.id.orderquantity);
        txtProductTime=itemView.findViewById(R.id.ordertime);

    }


    @Override
    public void onClick(View v) {
        itemClickListner.onClick(v,getAdapterPosition(),false);
    }

    public void setItemClickListner(ItemClickListner itemClickListner){
        this.itemClickListner=itemClickListner;
    }
}
