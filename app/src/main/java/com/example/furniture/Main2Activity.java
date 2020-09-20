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

/**
 *
 * @author
 * loading the products in the app
 * list of all the procducts displaying on the screen
 * storing data in the database
 */
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

    /**
     * creating the list and displaying message
     * getting the database to show all the list
     * displaying the likes.
     * @param savedInstanceState
     */
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
            /**
             * before text changed state
             * @param s
             * @param start
             * @param count
             * @param after
             */
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            /**
             *  on changed state
             * @param s
             * @param start
             * @param before
             * @param count
             */
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }
            /**
             * after the text has changed
             * displaying string or empty string
             * @param s
             */
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
            /**
             * displaying the message if shopping is completed by the user
             *
             * @param v
             */
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
            /**
             *
             * @param
             */
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
    /**
     * on start of the page
     * building the products at the backend and showing the list with products and accessories
     */
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
            /**
             * setting the name of the product
             * setting the price
             * creating the discription
             * the like button
             * @param blogViewHolder
             * @param i
             * @param product_get_set_v
             */
            @Override
            protected void onBindViewHolder(@NonNull final BlogViewHolder blogViewHolder, final int i, @NonNull final product product_get_set_v) {
                final String post_key = getRef(i).getKey();

                blogViewHolder.setname(product_get_set_v.getName());
                blogViewHolder.setPrice("$"+product_get_set_v.getPrice());
                blogViewHolder.setDesc(product_get_set_v.getDesc());
                blogViewHolder.setLikeBtn(post_key);

                String image_url = blogViewHolder.setimage(product_get_set_v.getImage());

                blogViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    /**
                     * listener for the product description, its data and the product id
                     * @param v
                     */
                    @Override
                    public void onClick(View v) {
                        final String productid = getRef(i).getKey();
                        Log.d("productid", " data : " + productid);
                        //Toast.makeText(Main2Activity.this, post_key, Toast.LENGTH_LONG).show();

                        Intent singleItemIntent = new Intent(Main2Activity.this,ProductDetailsActivity.class);
                        singleItemIntent.putExtra("product_id",post_key);
                        startActivity(singleItemIntent);
                    }
                });


                blogViewHolder.mLikebtn.setOnClickListener(new View.OnClickListener() {

                    /**
                     * validating the like button
                     * @param v
                     */
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
                            /**
                             * in case if want to cancel.
                             * @param error
                             */
                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });

                    }
                });
            }

            /**
             * layout for the app page
             * basically the blog
             * @param parent
             * @param viewType
             * @return
             */
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




    /**
     * displaying
     * image button
     * name of the product
     * and the like button
     */
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
        /**
         * validating the like button when its state changes.
         * setting the color for the button
         * @param post_key
         */
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
                /**
                 * if there is an error
                 * @param error
                 */
                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        }
        /**
         * dispying the name
         * @param name
         */

        public void setname(String name)
        {
            TextView ename=(TextView)mView.findViewById(R.id.text1);
            ename.setText(name);

        }
        /**
         * displaying the price
         * @param price
         */
        public void setPrice(String price)
        {
            TextView eprice=(TextView)mView.findViewById(R.id.text2);
            eprice.setText(price);

        }
        /**
         * displaying the description
         * @param desc
         */
        public void setDesc(String desc)
        {
            TextView edesc=(TextView)mView.findViewById(R.id.text3);
            edesc.setText(desc);

        }
        /**
         * actually loading the picture on the app
         * @param url
         * @return  the picture of the product
         */
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


    /**
     * To click to the shoppingCart activity
     *
     */
    public void onCreateDialog() {
        Intent intent5 = new Intent(this, ShoppingCart.class);
        startActivity(intent5);
    }
    /**
     * inflate the menu
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_activity, menu);
        return true;

    }

    /**
     * this method gets called when user select an option from the menu
     * @param item a MenuItem Selected
     * @return the selected Item
     */

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
