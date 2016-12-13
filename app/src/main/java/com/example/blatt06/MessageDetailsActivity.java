package com.example.blatt06;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MessageDetailsActivity extends AppCompatActivity {
    private TextView tvName;
    private TextView tvMessage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_details);
        Intent displayIntent = getIntent();
        String nameValue = displayIntent.getStringExtra("name");
        String messageValue = displayIntent.getStringExtra("message");
        tvName =(TextView) findViewById(R.id.displayNameTextView);
        tvMessage =(TextView) findViewById(R.id.displayMessageTextView);
        tvName.setText(nameValue);
        tvMessage.setText(messageValue);

    }
}
