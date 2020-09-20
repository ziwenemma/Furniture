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
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
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
 * @author ziwenma
 *
 * Payment page
         */
public class PaymentPage extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    Button btn;
    EditText customerName,cardNumber ,cardPin, cardExpiry;
    String name, number, pin, expiry;
    SharedPreferences sharedPreferences;

    /**
     * starting the activity
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_page);

        btn = (Button) findViewById(R.id.confirm);
        customerName = findViewById(R.id.customer_name);
        cardNumber = findViewById(R.id.card_number);
        cardPin = findViewById(R.id.pin);
        cardExpiry = findViewById(R.id.card_expiry);

        sharedPreferences = getSharedPreferences("MyPREFERENCES", Context.MODE_PRIVATE);
        final String sId = sharedPreferences.getString("id", "");
        if (!TextUtils.isEmpty(sId)) {
            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("payment");

            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {


                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                        Payment payment = dataSnapshot.getValue(Payment.class);

                        if (TextUtils.equals(payment.getId(), sId)) {
                            customerName.setText(payment.getName());
                            cardNumber.setText(payment.getCardnumber());
                            cardPin.setText(payment.getPin());
                            cardExpiry.setText(payment.getExpiry());

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
            /**
             * getting the input for the payment
             * customer name
             * card number
             * card Pin
             * card Expiry
             */
            @Override
            public void onClick(View v) {

                name = customerName.getText().toString();
                number = cardNumber.getText().toString();
                pin = cardPin.getText().toString();
                expiry = cardExpiry.getText().toString();


                if (TextUtils.isEmpty(name)) {
                    customerName.setError("This field is empty!");
                    return;
                }
                // blan space is for error messages
                if (TextUtils.isEmpty(number)) {
                    cardNumber.setError("This field is empty!");
                    return;
                }
                if (TextUtils.isEmpty(pin)) {
                    cardPin.setError("cannot be empty");
                    return;

                }
                if (TextUtils.isEmpty(expiry)) {
                    cardExpiry.setError("the expiry is wrong");
                    return;

                }

                DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("payment");
                String Id = mDatabase.push().getKey();
                Payment user = new Payment(Id, name, number, pin, expiry);
                mDatabase.child(Objects.requireNonNull(Id)).setValue(user);

                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("id", Id);
                editor.apply();

                Toast.makeText(PaymentPage.this, "Payment successful!", Toast.LENGTH_LONG).show();

                startMenuActivity();
            }
        });
    }

    /**
     * displaying the page
     */

    public void startMenuActivity() {
        Intent intent = new Intent(this, ThanksPage.class);
        startActivity(intent);

    }

    /**
     * options menu
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_activity, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.contactpage) {
            Intent myintent = new Intent(PaymentPage.this, Contact.class);
            startActivity(myintent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemSelected(AdapterView<?> arg0, View arg1,
                               int position,
                               long id) {


    }

    /**
     * if no option selected
     * @param arg0
     */
    @Override
    public void onNothingSelected(AdapterView<?> arg0) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch (view.getId()) {
            case R.id.radio_credit:
                if (checked)
                    // Pirates are the best
                    break;
            case R.id.radio_debit:
                if (checked)
                    // Ninjas rule
                    break;
        }
    }

}


