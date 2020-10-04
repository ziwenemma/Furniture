package com.example.furniture;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


public class Main2Activity extends AppCompatActivity {

    RecyclerView recyclerView;
    EditText editText;
    ArrayList<product> arrayList;
    private boolean mProcessLike=false;
    FirebaseAuth fAuth;

    Query query1;
    private DatabaseReference mdatabasereference;
    private ProgressDialog progressDialog;
    FirebaseRecyclerAdapter<product, BlogViewHolder> firebaseRecyclerAdapter;
    LinearLayoutManager mLayoutManager;
    private FloatingActionButton fab;
    Adapter adapter;
    List<product> productList;
    private DatabaseReference mDatabaseLike;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        arrayList=new ArrayList<>();
        progressDialog = new ProgressDialog(Main2Activity.this);
        progressDialog.setMessage("Loading Products");
        progressDialog.show();
        fAuth = FirebaseAuth.getInstance();

        mdatabasereference = FirebaseDatabase.getInstance().getReference("products").child("accessories");
         mDatabaseLike=FirebaseDatabase.getInstance().getReference().child("Likes");
        recyclerView = (RecyclerView) findViewById(R.id.recyclerViewGirdView);
        editText = findViewById(R.id.edittext);

        mDatabaseLike.keepSynced(true);

        editText.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(!s.toString().isEmpty()){
                    search(s.toString());
                }
                else {
                    search("");
                }
            }
        });



        fab= (FloatingActionButton)findViewById(R.id.fab_checkout);

        fab.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Main2Activity.this,ShoppingCart.class);
                startActivity(intent);
                onCreateDialog();
                Toast.makeText(getApplicationContext(),"You have Successfully go to shopping cart. Please check the item ",Toast.LENGTH_LONG).show();
            }
        });


    }

    private void search(String s) {
        Query query=mdatabasereference.orderByChild("name").startAt(s).endAt(s+"\uf8ff");
        query.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChildren()){
                    arrayList.clear();;
                    for (DataSnapshot dss:dataSnapshot.getChildren()){
                        final product product= dss.getValue(com.example.furniture.product.class);
                        arrayList.add(product);
                    }

                    Adapter adapter=new Adapter(getApplicationContext(),arrayList);
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();
        query1 = FirebaseDatabase.getInstance().getReference().child("products").child("accessories");
        FirebaseRecyclerOptions<product> options =
                new FirebaseRecyclerOptions.Builder<product>()
                        .setQuery(query1, product.class)
                        .build();

        Log.d("Options"," data : "+options);



        firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<product, BlogViewHolder>(options) {

            @Override
            protected void onBindViewHolder(@NonNull final BlogViewHolder blogViewHolder, final int i, @NonNull final product product_get_set_v) {
                final String post_key = getRef(i).getKey();

                blogViewHolder.setname(product_get_set_v.getName());
                blogViewHolder.setPrice("$"+product_get_set_v.getPrice());
                blogViewHolder.setDesc(product_get_set_v.getDesc());
                blogViewHolder.setRate(product_get_set_v.getRate());
                blogViewHolder.setLikeBtn(post_key);


                String image_url = blogViewHolder.setimage(product_get_set_v.getImage());

                blogViewHolder.itemView.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        final String productid = getRef(i).getKey();
                        Log.d("productid", " data : " + productid);
                        Intent singleItemIntent = new Intent(Main2Activity.this,ProductDetailsActivity.class);
                        singleItemIntent.putExtra("product_id",post_key);
                        startActivity(singleItemIntent);
                    }
                });


                blogViewHolder.mLikebtn.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        mProcessLike = true;
                        mDatabaseLike.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {

                                if (mProcessLike) {
                                    if (snapshot.child(post_key).hasChild(fAuth.getCurrentUser().getUid())) {
                                        mDatabaseLike.child(post_key).child(fAuth.getCurrentUser().getUid()).removeValue();
                                        mProcessLike = false;
                                    } else {
                                        mDatabaseLike.child(post_key).child(fAuth.getCurrentUser().getUid()).setValue("RandomValue");
                                        mProcessLike = false;

                                    }
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });

                    }
                });
            }


            @NonNull
            @Override
            public BlogViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_layout, parent, false);
                progressDialog.dismiss();
                return new BlogViewHolder(view);


            }
        };
        firebaseRecyclerAdapter.startListening();
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(),1);
        recyclerView.setLayoutManager(gridLayoutManager);

        recyclerView.setAdapter(firebaseRecyclerAdapter);
    }





    public static class BlogViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {

        ImageButton mLikebtn;

        View mView;
        TextView ename;
        DatabaseReference mDatabaseLike;
        FirebaseAuth fAuth;



        public BlogViewHolder(View itemView)
        {
            super(itemView);

            mView=itemView;
            mLikebtn= (ImageButton) mView.findViewById(R.id.likebtn);
            mDatabaseLike =FirebaseDatabase.getInstance().getReference().child("Likes");
            mDatabaseLike.keepSynced(true);
            fAuth = FirebaseAuth.getInstance();




        }

        public void setLikeBtn(final String post_key){
            mDatabaseLike.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                   if(snapshot.child(post_key).hasChild(fAuth.getCurrentUser().getUid())){
                       mLikebtn.setImageResource(R.drawable.red);
                   }else{
                       mLikebtn.setImageResource(R.drawable.grey);

                   }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        }

        public void setname(String name)
        {
            TextView ename=(TextView)mView.findViewById(R.id.text1);
            ename.setText(name);

        }

        public void setPrice(String price)
        {
            TextView eprice=(TextView)mView.findViewById(R.id.text2);
            eprice.setText(price);

        }

        public void setDesc(String desc)
        {
            TextView edesc=(TextView)mView.findViewById(R.id.text3);
            edesc.setText(desc);

        }

        public void setRate(String rate)
        {
            TextView erate=(TextView)mView.findViewById(R.id.textViewRating);
            erate.setText(rate);

        }

        public String setimage(String url)
        {
            ImageView image = (ImageView)mView.findViewById(R.id.productimage);
            Picasso.get().load(url).into(image);
            return url;
        }

        @Override
        public void onClick(View v) {

        }
    }



    public void onCreateDialog() {
        Intent intent5 = new Intent(this, ShoppingCart.class);
        startActivity(intent5);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_activity, menu);
        return true;

    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_profile) {
            Intent myintent = new Intent(Main2Activity.this, ProfilePage.class);
            startActivity(myintent);
        }
        else if (id == R.id.contactpage) {
            Intent myintent = new Intent(Main2Activity.this, Contact.class);
            startActivity(myintent);
        }
        else if (id == R.id.ordermenu) {
            Intent myintent = new Intent(Main2Activity.this, OrderHistory.class);
            startActivity(myintent);
        }
        else if (id == R.id.aboutPage) {
            Intent myintent = new Intent(Main2Activity.this, AboutPage.class);
            startActivity(myintent);
        }
        else if (id == R.id.shoppingCart) {
            Intent myintent = new Intent(Main2Activity.this, ShoppingCart.class);
            startActivity(myintent);
        }


        return super.onOptionsItemSelected(item);
    }

}
