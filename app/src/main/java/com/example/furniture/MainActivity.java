package com.example.furniture;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;



public class MainActivity extends AppCompatActivity {


    Button btn;

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

    public void startMenuActivity(View view){
        Intent intent= new Intent(this,SignUpPage.class);
        startActivity(intent);

    }


    public  void startNextActivity(View view){
        Intent Next = new Intent(this, LoginPage.class);
        startActivity(Next);

    }
}


