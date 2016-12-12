package com.example.blatt06;

import android.app.ListActivity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);
        Intent intent = getIntent();
        listView = (ListView) findViewById(R.id.listView);
        loadData();

    }

    public void update(JSONArray data){
        Log.e(TAG, data.toString());
        List<String> list = new ArrayList<>();
        for(int i = 0; i < data.length(); i++){
            JSONObject obj = null;
            try {
                obj = data.getJSONObject(i);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if(obj.has("message")){
                try {
                    list.add(obj.getString("message"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }

        ListAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list);
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
