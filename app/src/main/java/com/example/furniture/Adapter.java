package com.example.furniture;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Contains the list of items displayed
 * product list
 */

public class Adapter extends RecyclerView.Adapter<Adapter.AdapterViewHolder> {
    public Context c;
    public ArrayList<product> arrayList;
    int num = 0;

 /**
     *
     * @param c context
     * @param arrayList is Array list as explained above
     */
    public Adapter(Context c, ArrayList<product> arrayList) {
        this.c = c;
        this.arrayList = arrayList;

    }
      /**
     *
     * @return  number of items
     */
    @Override
    public int getItemCount() {
        return arrayList.size();
    }
     /**
     *
     * @param position selected item
     * @return position
     */

    @Override
    public long getItemId(int position) {
        return position;
    }
    /**
     *
     * @param parent
     * @param viewType
     * @return view group
     */

    @NonNull
    @Override
    public AdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_layout, parent, false);
        return new AdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final AdapterViewHolder holder, int position) {
        product product = arrayList.get(position);
        holder.t1.setText(product.getName());
        holder.t2.setText(product.getPrice());
        holder.t3.setText(product.getDesc());
        Picasso.get().load(product.getImage()).into(holder.i1);
    }
     
    /**
     * text view for the items displayed
     */
    
    public class AdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView t1;
        public TextView t2;
        public TextView t3;
        public ImageView i1;
        Button button;

        public AdapterViewHolder(@NonNull View itemView) {
            super(itemView);

            t1 = (TextView) itemView.findViewById(R.id.text1);
            t2 = (TextView) itemView.findViewById(R.id.text2);
            t3 = (TextView) itemView.findViewById(R.id.text3);
            i1 = (ImageView) itemView.findViewById(R.id.productimage);


        }

        @Override
        public void onClick(View v) {

            }
        }

    }

