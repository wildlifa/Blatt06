package com.example.blatt06;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import okhttp3.OkHttpClient;

public class SendActivity extends AppCompatActivity {
    private static final String TAG = SendActivity.class.getSimpleName();
    private final OkHttpClient client = new OkHttpClient();
    private EditText etName;
    private EditText etMessage;
    private Button b;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send);
        Intent intent = getIntent();


    }
}
