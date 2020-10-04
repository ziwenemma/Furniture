package com.example.furniture;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class ThanksPage extends AppCompatActivity {
    Button btn;
    Button btn2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thanks_page);

        btn = (Button) findViewById(R.id.new_button);
        btn2=(Button)findViewById(R.id.second_button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startMenuActivity();
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startNextActivity();
            }
        });
    }
    public void startMenuActivity(){
        Intent intent= new Intent(this, Main2Activity.class);
        startActivity(intent);

    }
    public void startNextActivity(){
        Intent intent= new Intent(this, Contact.class);
        startActivity(intent);

    }
}


