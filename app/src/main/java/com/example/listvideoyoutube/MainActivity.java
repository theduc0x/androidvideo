package com.example.listvideoyoutube;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.listvideoyoutube.my_interface.IClickItemVideoListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RecyclerView rvVideo;
    ArrayList<Video> listVideo;
    VideoAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listVideo = new ArrayList<>();
        rvVideo = findViewById(R.id.rv_video);
        adapter = new VideoAdapter(listVideo, new IClickItemVideoListener() {
            @Override
            public void onClickItemVideo(int position) {
                String idVideo = listVideo.get(position).getIdVideo();
                Intent toPlayVideo
                        = new Intent(getApplicationContext(), PlayVideoActivity.class);
                toPlayVideo.putExtra(Util.EXTRA_ID_VIDEO, idVideo);
                startActivity(toPlayVideo);
            }
        });
        LinearLayoutManager linearLayoutManager
                = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        RecyclerView.ItemDecoration decoration
                = new DividerItemDecoration(this, RecyclerView.VERTICAL);

        rvVideo.addItemDecoration(decoration);
        rvVideo.setLayoutManager(linearLayoutManager);
        rvVideo.setAdapter(adapter);

        readJsonApiYoutube(Util.url);
    }
    // Đọc JSON từ playlist
    private void readJsonApiYoutube(String url) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String title = "";
                            String urlImage = "";
                            String idVideo = "";

                            JSONArray arrayItem = response.getJSONArray("items");
                            for (int i = 0; i < arrayItem.length(); i++) {
                                JSONObject objectItem = arrayItem.getJSONObject(i);
                                JSONObject objectSnippet = objectItem.getJSONObject("snippet");
                                title = objectSnippet.getString("title");

                                JSONObject objectThumbnails
                                        = objectSnippet.getJSONObject("thumbnails");
                                JSONObject objectMedium = objectThumbnails.getJSONObject("default");
                                urlImage = objectMedium.getString("url");

                                JSONObject objectResourceId
                                        = objectSnippet.getJSONObject("resourceId");
                                idVideo = objectResourceId.getString("videoId");

                                listVideo.add(new Video(title, idVideo, urlImage));

                                adapter.notifyItemInserted(i);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        requestQueue.add(jsonObjectRequest);
    }

}