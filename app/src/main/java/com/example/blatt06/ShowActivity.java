package com.example.blatt06;

import android.app.ListActivity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ShowActivity extends AppCompatActivity {
    private ListView listView;
    private static final String TAG = ShowActivity.class.getSimpleName();
    private final OkHttpClient client = new OkHttpClient();
    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    List<String> messageList = new ArrayList<>();
    List<String> nameList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);
        Intent intent = getIntent();
        listView = (ListView) findViewById(R.id.listView);
        loadData();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent displayIntent = new Intent(ShowActivity.this, MessageDetailsActivity.class);
                displayIntent.putExtra("name","TODO");
                ShowActivity.this.startActivity(displayIntent);
            }
        });

    }

    public void update(JSONArray data){
        Log.e(TAG, data.toString());

        for(int i = 0; i < data.length(); i++){
            JSONObject obj = null;
            try {
                obj = data.getJSONObject(i);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if(obj.has("message")){
                try {
                    messageList.add(obj.getString("message"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            if(obj.has("name")){
                try {
                    nameList.add(obj.getString("name"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }

        ListAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, messageList);
        listView.setAdapter(adapter);

    }
    private void loadData() {
        JSONObject data = new JSONObject();

        try {
            data.put("web", "requestEntrys");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String json = data.toString();
        RequestBody body = RequestBody.create(JSON, json);

        Request request = new Request.Builder().url("http://projects.hcilab.org/pmi/").post(body).build();
        client.newCall(request).enqueue(new Callback() {
            JSONArray data = null;

            @Override
            public void onFailure(Call call, IOException e) {
                Log.e(TAG, "ERROR #001" + e.toString());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String body = response.body().string();
                if (!response.isSuccessful()){ Log.e(TAG, "ERROR #002" + body);} else {

                    try {
                        data = new JSONArray(body);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                    runOnUiThread(new Runnable(){
                        public void run(){
                            update(data);
                        }

                    });
                }
            }
        });
    }
}
