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

public class OrderHistory extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private Button goback;
    private TextView Amount;
    private TextView totalAmount;
    FirebaseAuth fAuth;

    private float overTotalPrice=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_history);
        fAuth = FirebaseAuth.getInstance();


        recyclerView = findViewById(R.id.recycler);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        goback = (Button) findViewById(R.id.orderBack);


        goback.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent=new Intent(OrderHistory.this,Main2Activity.class);
                startActivity(intent);
                finish();

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



        FirebaseRecyclerAdapter<Cart,Order>adapter
                =new FirebaseRecyclerAdapter<Cart, Order>(options) {

            @Override
            protected void onBindViewHolder(@NonNull Order order, int i, @NonNull final Cart cart) {


                order.txtProductName.setText("Product =" +cart.getPname());
                order.txtProductQuantity.setText("Quantity ="+cart.getQuantity());
                order.txtProductPrice.setText("Price ="+cart.getPrice()+"$");
                order.txtProductTime.setText("Order Date="+cart.getTime());



            }


            @NonNull

            @Override
            public Order onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.order_history_list,parent,false);

                Order holder=new Order(view);
                return holder;
            }
        };

        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }
}
