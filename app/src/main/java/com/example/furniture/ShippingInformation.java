package com.example.furniture;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;


public class ShippingInformation extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    Button btn;

    String[] cities = {"Montreal","Laval","Quebec"};
    String[] times={"Monday","Tuesday","Wednesday","Thursday","Friday","Saturday","Sunday"};
    EditText etName, etPhone, etAddress, etEmail,etCity,etZipeCode,etTime;
    String name, phone, address, email, city,zipcode,time;
    Spinner spino;
    Spinner sp2;
    SharedPreferences sharedPreferences;
    FirebaseAuth fAuth;



    String totalAmount
            ="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shipping_information);
        fAuth = FirebaseAuth.getInstance();


        totalAmount=getIntent().getStringExtra("Total Price");
        Toast.makeText(this,"Total Price =$"+totalAmount,Toast.LENGTH_LONG).show();

        spino = findViewById(R.id.city);
        spino.setOnItemSelectedListener(this);
        sp2 = findViewById(R.id.setTime);
        sp2.setOnItemSelectedListener(this);

        etName = findViewById(R.id.name);
        etPhone = findViewById(R.id.phone);
        etAddress = findViewById(R.id.address);
        etEmail = findViewById(R.id.email);
        etZipeCode = findViewById(R.id.zipCode);
        fAuth = FirebaseAuth.getInstance();




        sharedPreferences = getSharedPreferences("MyPREFERENCES", Context.MODE_PRIVATE);
        final String sId = sharedPreferences.getString("id", "");
        if (!TextUtils.isEmpty(sId)) {
            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("shippings");

            databaseReference.child("user shippment").child(fAuth.getCurrentUser().getUid()).child("shippment").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {


                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                        Shipping shipping = dataSnapshot.getValue(Shipping.class);

                        if (TextUtils.equals(shipping.getId(), sId)) {
                            etName.setText(shipping.getName());
                            etAddress.setText(shipping.getAddress());
                            etEmail.setText(shipping.getEmail());
                            etPhone.setText(shipping.getPhone());
                            etZipeCode.setText(shipping.getZipcode());

                        }


                    }


                }


                @Override
                public void onCancelled(DatabaseError databaseError) {


                }
            });

        }

        btn = (Button) findViewById(R.id.confirm);
        btn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)


            @Override
            public void onClick(View v) {

                name = etName.getText().toString();
                phone = etPhone.getText().toString();
                address = etAddress.getText().toString();
                email = etEmail.getText().toString();
                city = spino.getSelectedItem().toString();
                time=sp2.getSelectedItem().toString();


                if (TextUtils.isEmpty(name)) {
                    etName.setError("This field is empty!");
                    return;
                }
                if (TextUtils.isEmpty(email)) {
                    etEmail.setError("This field is empty!");
                    return;
                }
                if (TextUtils.isEmpty(phone)) {
                    etPhone.setError("the Phone number length should be 10");
                    return;

                }

                DatabaseReference mDatabase= FirebaseDatabase.getInstance().getReference("shippment");

                 String Id = mDatabase.push().getKey();
                Shipping user = new Shipping(Id, name, email, phone, address, city,zipcode,time);
                mDatabase.child("user shippment").child(fAuth.getCurrentUser().getUid()).child(Objects.requireNonNull(Id)).setValue(user);;
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("id", Id);
                editor.apply();

                Toast.makeText(ShippingInformation.this, "Shipping Information Saved Successfully!", Toast.LENGTH_LONG).show();

                startMenuActivity();
            }

            private void addingToShipList() {



            }
        });

        ArrayAdapter ad
                = new ArrayAdapter(
                this,
                android.R.layout.simple_spinner_item,
                cities);

        ad.setDropDownViewResource(
                android.R.layout
                        .simple_spinner_dropdown_item);
        spino.setAdapter(ad);

        ArrayAdapter at
                = new ArrayAdapter(
                this,
                android.R.layout.simple_spinner_item,
                times);
        at.setDropDownViewResource(
                android.R.layout
                        .simple_spinner_dropdown_item);
        sp2.setAdapter(at);


    }

    public void startMenuActivity() {
        Intent intent = new Intent(this, PaymentPage.class);
        startActivity(intent);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_activity, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.contactpage) {
            Intent myintent = new Intent(ShippingInformation.this, Contact.class);
            startActivity(myintent);
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onItemSelected(AdapterView<?> arg0, View arg1,
                               int position,
                               long id) {


    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
