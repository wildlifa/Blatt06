package com.example.blatt06;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class SendActivity extends AppCompatActivity {
    private static final String TAG = SendActivity.class.getSimpleName();
    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");
    private final OkHttpClient client = new OkHttpClient();

    private EditText etName;
    private EditText etMessage;
    private Button b;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send);
        Intent intent = getIntent();

        etName = (EditText)findViewById(R.id.nameEditText);
        etMessage = (EditText) findViewById(R.id.messageEditText);
        b = (Button) findViewById(R.id.sendButton);
        b.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                send(etName.getText().toString(), etMessage.getText().toString());
            }

            private void send(String pName, String pMessage) {
                JSONObject data = new JSONObject();
                try {
                    data.put("web", "sendEntry");
                    data.put("name", pName);
                    data.put("message", pMessage);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                String json = data.toString();
                RequestBody body = RequestBody.create(JSON, json);

                Request request = new Request.Builder().url("http://projects.hcilab.org/pmi/").post(body).build();
                client.newCall(request).enqueue(new Callback(){

                    @Override
                    public void onFailure(Call call, IOException e) {
                        Log.e(TAG, "ERROR #001" + e.toString());
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String body = response.body().string();
                        if(!response.isSuccessful()){
                            Log.e(TAG, "ERROR#002: "+ body);
                        } else {
                            try {
                                JSONObject data = new JSONObject(body);
                                if(data.has("error")){
                                    if(data.getInt("error")!=0){
                                        Log.e(TAG, "Server rejected data response is "+ body);
                                    } else {
                                        Log.i(TAG, "Server accepted data response is "+ body);
                                        Intent i = new Intent(SendActivity.this, MainActivity.class);
                                        startActivity(i);
                                    }
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
            }
        });
    }
}