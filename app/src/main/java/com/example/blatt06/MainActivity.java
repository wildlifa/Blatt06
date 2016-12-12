package com.example.blatt06;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private Button toSendButton;
    private Button toShowButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toSendButton = (Button) findViewById(R.id.toSendButton);
        toSendButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(MainActivity.this, SendActivity.class);
                MainActivity.this.startActivity(myIntent);
            }
        });

        toShowButton = (Button) findViewById(R.id.toShowButton);
        toShowButton.setOnClickListener(new View.OnClickListener() {
            
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(MainActivity.this, ShowActivity.class);
                MainActivity.this.startActivity(myIntent);
            }
        });

    }

}
