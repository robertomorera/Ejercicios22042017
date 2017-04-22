package com.example.cice.despertarvideo;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.VideoView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Reproduzco el video guardado en recursos.
        VideoView videoView=(VideoView)findViewById(R.id.video);
        String path="android.resource://"+getPackageName()+"/"+R.raw.videobd;
        Uri uri_video=Uri.parse(path);
        videoView.setVideoURI(uri_video);
        videoView.setMediaController(new MediaController(this));
        videoView.requestFocus();
        videoView.start();
    }
}
