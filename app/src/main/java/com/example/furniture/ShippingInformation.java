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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

/**
 *
 * shipping information for the app
 * adding the listener
 * displaying the list of the countries
 */
public class ShippingInformation extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    Button btn;

    String[] cities = {"Montreal","Laval","Quebec"};
    EditText etName, etPhone, etAddress, etEmail,etCity,etZipeCode,etTime;
    String name, phone, address, email, city,zipcode,time;
    Spinner spino;
    SharedPreferences sharedPreferences;

    String totalAmount
            ="";
    /**
     * adding the button and crearing the state
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shipping_information);

        totalAmount=getIntent().getStringExtra("Total Price");
        Toast.makeText(this,"Total Price =$"+totalAmount,Toast.LENGTH_LONG).show();

        spino = findViewById(R.id.city);
        spino.setOnItemSelectedListener(this);
        spino = findViewById(R.id.setTime);
        spino.setOnItemSelectedListener(this);

        etName = findViewById(R.id.name);
        etPhone = findViewById(R.id.phone);
        etAddress = findViewById(R.id.address);
        etEmail = findViewById(R.id.email);
        etZipeCode = findViewById(R.id.zipCode);


        sharedPreferences = getSharedPreferences("MyPREFERENCES", Context.MODE_PRIVATE);
        final String sId = sharedPreferences.getString("id", "");
        if (!TextUtils.isEmpty(sId)) {
            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("shippings");

            databaseReference.addValueEventListener(new ValueEventListener() {
                /**
                 *
                 * @param snapshot
                 */
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

                /**
                 * error(on cancelation)
                 * @param databaseError
                 */
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
                time=spino.getSelectedItem().toString();

                if (TextUtils.isEmpty(name)) {
                    etName.setError("This field is empty!");
                    return;
                }
                // blan space is for error messages
                if (TextUtils.isEmpty(email)) {
                    etEmail.setError("This field is empty!");
                    return;
                }
                if (TextUtils.isEmpty(phone)) {
                    etPhone.setError("the Phone number length should be 10");
                    return;

                }

                DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("shippings");
                String Id = mDatabase.push().getKey();
                Shipping user = new Shipping(Id, name, email, phone, address, city,zipcode,time);
                mDatabase.child(Objects.requireNonNull(Id)).setValue(user);

                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("id", Id);
                editor.apply();

                Toast.makeText(ShippingInformation.this, "Shipping Information Saved Successfully!", Toast.LENGTH_LONG).show();

                startMenuActivity();
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


    }

    public void startMenuActivity() {
        Intent intent = new Intent(this, PaymentPage.class);
        startActivity(intent);

    }

    /**
     * creating the menu option
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_activity, menu);
        return true;
    }

    /**
     * if an item is selected
     * @param item
     * @return the option selected(display)
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.contactpage) {
            Intent myintent = new Intent(ShippingInformation.this, Contact.class);
            startActivity(myintent);
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * displaying the info the selected item holds
     * @param arg0
     * @param arg1
     * @param position
     * @param id
     */
    @Override
    public void onItemSelected(AdapterView<?> arg0, View arg1,
                               int position,
                               long id) {


    }
    /**
     * if no item is selected
     * @param arg0
     */
    @Override
    public void onNothingSelected(AdapterView<?> arg0) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
