package com.example.furniture;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
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
 * Contact page for user to submit questions
 */

public class Contact extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    Button btn;
    Button button;
    EditText question, email;
    String userquestion, useremail;
    SharedPreferences sharedPreferences;

    /**
     * starting activity
     *
     * @param savedInstanceState
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        btn = (Button) findViewById(R.id.submit);
        question = findViewById(R.id.question);
        email = findViewById(R.id.email);
        button=findViewById(R.id.gobrowser);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent emailintent= new Intent(Intent.ACTION_VIEW, Uri.parse("https://mail.google.com/"));
                startActivity(emailintent);
            }
        });
        sharedPreferences = getSharedPreferences("MyPREFERENCES", Context.MODE_PRIVATE);
        final String sId = sharedPreferences.getString("id", "");
        if (!TextUtils.isEmpty(sId)) {
            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Contact Question");

            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {


                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                        retrieve retrieve = dataSnapshot.getValue(retrieve.class);

                        if (TextUtils.equals(retrieve.getId(), sId)) {
                            question.setText(retrieve.getQuestion());
                            email.setText(retrieve.getEmail());


                        }


                    }


                }

                @Override
                public void onCancelled(DatabaseError databaseError) {


                }
            });

        }

        btn = (Button) findViewById(R.id.submit);
        btn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            /**
             * getting the input for the contact
             * user Id
             * question
             * email account
             */
            @Override
            public void onClick(View v) {

                useremail = email.getText().toString();
                userquestion = question.getText().toString();


                if (TextUtils.isEmpty(useremail)) {
                    email.setError("This field is empty!");
                    return;
                }
                // blan space is for error messages
                if (TextUtils.isEmpty(userquestion)) {
                    question.setError("No question write!");
                    return;
                }


                DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("Contact Question");
                String Id = mDatabase.push().getKey();
                retrieve user = new retrieve(Id, useremail, userquestion);
                mDatabase.child(Objects.requireNonNull(Id)).setValue(user);

                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("id", Id);
                editor.apply();

                Toast.makeText(Contact.this, "Question submit successful!", Toast.LENGTH_LONG).show();

                startMenuActivity();
            }
        });
    }

    /**
     * if no option selected
     *
     * @param arg0
     */
    @Override
    public void onNothingSelected(AdapterView<?> arg0) {

    }

    /**
     * displaying the next page
     */

    public void startMenuActivity() {
        Intent intent = new Intent(this, ThanksPage.class);
        startActivity(intent);

    }

    /**
     * options menu
     *
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
            Intent myintent = new Intent(Contact.this, Contact.class);
            startActivity(myintent);
        }
        if (id == R.id.aboutPage) {
            Intent myintent = new Intent(Contact.this, AboutPage.class);
            startActivity(myintent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemSelected(AdapterView<?> arg0, View arg1,
                               int position,
                               long id) {


    }
}



