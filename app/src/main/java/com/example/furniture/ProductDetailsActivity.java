package com.example.furniture;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

import static com.bumptech.glide.Glide.*;

public class ProductDetailsActivity extends AppCompatActivity {


    private String mPost_key = null;
    private DatabaseReference mdatabasereference;
    FirebaseAuth fAuth;

    private Button addToCartButton;
    private ElegantNumberButton numberButton;
    private TextView mProName;
    private TextView mProDesc;
    private TextView mProPrice;
    private ImageView mProImage;
    /**
     * product details
     * extending the appCompatActivity
     */

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);
        addToCartButton=(Button)findViewById(R.id.cartbtn);
        numberButton=(ElegantNumberButton)findViewById(R.id.number_btn);
        fAuth = FirebaseAuth.getInstance();

        mdatabasereference=FirebaseDatabase.getInstance().getReference("products").child("accessories");

        String post_key=getIntent().getExtras().getString("product_id");
        mPost_key=getIntent().getExtras().getString("product_id");
        mProName=(TextView)findViewById(R.id.product_name_de);
        mProDesc=(TextView)findViewById(R.id.product_desc_de);
        mProPrice=(TextView)findViewById(R.id.product_price_de);
        mProImage=(ImageView)findViewById(R.id.product_image_de);

        addToCartButton.setOnClickListener(new View.OnClickListener() {
            /**
             * simple calender
             * product id, name, price
             * date, time, quantity, discount
             * @param v
             */
            @Override
            public void onClick(View v) {
                addingToCartList();
            }

            private void addingToCartList() {
                String saveCurrentTime,  saveCurrentDate;

                Calendar calForData= Calendar.getInstance();
                SimpleDateFormat currentDate=new SimpleDateFormat("MMM dd, yyyy");
                saveCurrentDate=currentDate.format(calForData.getTime());
                Calendar calForTime= Calendar.getInstance();
                SimpleDateFormat currentTime=new SimpleDateFormat("HH:mm:ss a");
                saveCurrentTime=currentTime.format(calForTime.getTime());

                final DatabaseReference cartListRef=FirebaseDatabase.getInstance().getReference().child("Cart List");

                final HashMap<String, Object> cartMap=new HashMap<>();
                cartMap.put("product_id",mPost_key);
                cartMap.put("pname",mProName.getText().toString());
                cartMap.put("price",mProPrice.getText().toString());
                cartMap.put("date",saveCurrentDate);
                cartMap.put("time",saveCurrentTime);
                cartMap.put("quantity",numberButton.getNumber());
                cartMap.put("discount","");

                cartListRef.child("user view").child(fAuth.getCurrentUser().getUid())
                        .child("product").child(mPost_key).updateChildren(cartMap)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            /**
                             * adding to the cart
                             * @param task
                             */
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    cartListRef.child("Admin view").child(fAuth.getCurrentUser().getUid())
                                            .child("product").child(mPost_key).updateChildren(cartMap)
                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (task.isSuccessful()){

                                                        Toast.makeText(ProductDetailsActivity.this, "Add to Cart List", Toast.LENGTH_SHORT).show();
                                                        Intent intent=new Intent(ProductDetailsActivity.this,Main2Activity.class);
                                                        startActivity(intent);
                                                    }

                                                }
                                            });

                                }
                            }
                        });

            }
        });



        mdatabasereference.child(mPost_key).addValueEventListener(new ValueEventListener() {
            /**
             * product details
             *
             * @param snapshot
             */
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {


                String post_name= (String) snapshot.child("name").getValue();
                String post_desc=(String)snapshot.child("desc").getValue();
                String post_price=(String)snapshot.child("price").getValue();
                String post_image=(String)snapshot.child("image").getValue();


                mProName.setText(post_name);
                mProDesc.setText(post_desc);
                mProPrice.setText(post_price);
                Glide.with(ProductDetailsActivity.this).load(post_image).into(mProImage);

            }

            /**
             * if cancelled
             * @param error
             */
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
}