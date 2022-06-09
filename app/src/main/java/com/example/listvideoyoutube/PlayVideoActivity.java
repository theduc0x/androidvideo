package com.example.listvideoyoutube;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.google.android.youtube.player.YouTubeThumbnailLoader;
import com.google.android.youtube.player.YouTubeThumbnailView;

public class PlayVideoActivity extends YouTubeBaseActivity
        implements YouTubePlayer.OnInitializedListener {
    YouTubePlayerView ypvPlayVideo;
    String idVideo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_video);
        ypvPlayVideo = findViewById(R.id.ypv_video);

        Intent getIdVideo = getIntent();
        idVideo = getIdVideo.getStringExtra(Util.EXTRA_ID_VIDEO);

        ypvPlayVideo.initialize(Util.API_KEY, this);

    }

    // Nếu thành công thì sẽ chạy video qua id
    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider,
                                        YouTubePlayer youTubePlayer,
                                        boolean b) {
            youTubePlayer.loadVideo(idVideo);
            youTubePlayer.setFullscreen(true);
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider,
                                        YouTubeInitializationResult youTubeInitializationResult) {
        // Nếu lỗi do người dùng thì chúng ta sẽ gửi code
        if (youTubeInitializationResult.isUserRecoverableError()) {
            youTubeInitializationResult.getErrorDialog(this, Util.REQUEST_CODE_ERROR);
        } else {
            Toast.makeText(this, "Error!", Toast.LENGTH_SHORT).show();
        }
    }
    // Nếu lỗi do người dùng ta sẽ chạy lại
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Util.REQUEST_CODE_ERROR) {
            ypvPlayVideo.initialize(Util.API_KEY, this);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}