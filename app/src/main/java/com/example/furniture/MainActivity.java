package com.example.furniture;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

/**
 * The Main Activity for the Application
 * @author ziwenma
 * @version 1.0
 * This is the first screen the user sees
 */

public class MainActivity extends AppCompatActivity {

    /**
     * Watch me document this code
     */

    /**
     * Button for user to click
     */
    Button btn;
    /**
     * Button for user to click
     */
    Button LogIn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn = (Button) findViewById(R.id.buttonToMain);
        LogIn = (Button)findViewById(R.id.loginButton);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startMenuActivity(v);
            }
        });

        LogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startNextActivity(v);
            }
        });
    }

    /**
     * opens the next activity
     * @see Main2Activity
     * @param view
     */
    public void startMenuActivity(View view){
        Intent intent= new Intent(this,SignUpPage.class);
        startActivity(intent);

    }

    /**
     * opens the next activity
     * @see LoginPage
     * @param view
     */
    public  void startNextActivity(View view){
        Intent Next = new Intent(this, LoginPage.class);
        startActivity(Next);

    }
}


