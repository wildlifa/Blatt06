package com.example.blatt06;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.regex.*;


public class MessageDetailsActivity extends AppCompatActivity {
    private TextView tvName;
    private TextView tvMessage;
    private TextView tvHashtag;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_details);
        Intent displayIntent = getIntent();
        String nameValue = displayIntent.getStringExtra("name");
        String messageValue = displayIntent.getStringExtra("message");
        String hashtagValue = "";

        Pattern pattern = Pattern.compile("#(\\S+)");
        Matcher matcher = pattern.matcher(messageValue);
        while (matcher.find()) {

            hashtagValue += matcher.group(1) + ", ";
        }

        tvName =(TextView) findViewById(R.id.displayNameTextView);
        tvMessage =(TextView) findViewById(R.id.displayMessageTextView);
        tvHashtag = (TextView) findViewById(R.id.displayHashtagTextView);
        tvName.setText(nameValue);
        tvMessage.setText(messageValue);
        tvHashtag.setText(hashtagValue);

    }
}
