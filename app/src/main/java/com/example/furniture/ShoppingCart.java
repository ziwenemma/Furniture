package com.example.furniture;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

public class ShoppingCart extends AppCompatActivity {
    Button btn;

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private Button NextProcessBtn;
    private TextView totalAmount;
    FirebaseAuth fAuth;

    private float overTotalPrice=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_cart);
        fAuth = FirebaseAuth.getInstance();


        btn=(Button)findViewById(R.id.go_shippmentback);
        recyclerView = findViewById(R.id.cart_list_recyclerview);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        NextProcessBtn = (Button) findViewById(R.id.go_shippmentbtn);
        totalAmount = (TextView) findViewById(R.id.total_price);

        NextProcessBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ShoppingCart.this,ShippingInformation.class);
                intent.putExtra("Total Price",String.valueOf(overTotalPrice));
                startActivity(intent);
                finish();

            }
        });

        btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ShoppingCart.this,Main2Activity.class);
                startActivity(intent);
            }
        });

    }


    @Override
    protected void onStart() {
        super.onStart();

        final DatabaseReference cartListRef= FirebaseDatabase.getInstance().getReference().child("Cart List");


        FirebaseRecyclerOptions<Cart>options=new FirebaseRecyclerOptions.Builder<Cart>()
                .setQuery(cartListRef.child("user view")
                        .child(fAuth.getCurrentUser().getUid()).child("product"),Cart.class).build();



        FirebaseRecyclerAdapter<Cart,CartViewHolder>adapter
                =new FirebaseRecyclerAdapter<Cart, CartViewHolder>(options) {

            @Override
            protected void onBindViewHolder(@NonNull CartViewHolder cartViewHolder, int i, @NonNull final Cart cart) {


                cartViewHolder.txtProductName.setText("Product =" +cart.getPname());
                cartViewHolder.txtProductQuantity.setText("Quantity ="+cart.getQuantity());
                cartViewHolder.txtProductPrice.setText("Price ="+cart.getPrice()+"$");

                int oneTyprProductTPrice=(Integer.parseInt(cart.getQuantity()))*Integer.parseInt(cart.getPrice());
                overTotalPrice=overTotalPrice+oneTyprProductTPrice;
                totalAmount.setText(String.valueOf(overTotalPrice));


                cartViewHolder.itemView.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        CharSequence options[]=new CharSequence[]{
                                "Edit",
                                "Remove"
                        };
                        AlertDialog.Builder builder=new AlertDialog.Builder(ShoppingCart.this);
                        builder.setTitle("Cart Options:");

                        builder.setItems(options, new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                if(which == 0){
                                    Intent intent=new Intent(ShoppingCart.this, ProductDetailsActivity.class);
                                    intent.putExtra("product_id",cart.getProduct_id());
                                    startActivity(intent);
                                }
                                if(which==1){
                                    cartListRef.child("user view").child(fAuth.getCurrentUser().getUid()).child("product")
                                            .child(cart.getProduct_id()).removeValue()
                                            .addOnCompleteListener(new OnCompleteListener<Void>() {

                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {

                                                    if(task.isSuccessful()){
                                                        Toast.makeText(ShoppingCart.this,"Item removed",Toast.LENGTH_LONG).show();
                                                        Intent intent=new Intent(ShoppingCart.this, Main2Activity.class);
                                                        startActivity(intent);


                                                    }

                                                }
                                            });
                                }
                            }
                        });

                        builder.show();
                    }
                });

            }


            @NonNull

            @Override
            public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_item_layout,parent,false);

                CartViewHolder holder=new CartViewHolder(view);
                return holder;
            }
        };
        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }
}
