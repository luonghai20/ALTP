package com.haixl.ailatrieuphu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class HomeActivity extends AppCompatActivity {
    private int backpress=0;
    MediaPlayer backgroundMusic;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        backgroundMusic =MediaPlayer.create(this,R.raw.bgmusic);
        backgroundMusic.setLooping(true);
        backgroundMusic.start();
    }

    public void starGame(View view) {
        Intent intent=new Intent(HomeActivity.this,MainActivity.class);
        startActivity(intent);
    }

    public void rankActivity(View view) {
        Intent intent=new Intent(HomeActivity.this, HighScoreActivity.class);
        startActivity(intent);
    }

    public void guidePlayActivity(View view) {
        Intent intent=new Intent(HomeActivity.this,GuidePlayActivity.class);
        startActivity(intent);
    }

    public void AboutActivity(View view) {
        Intent intent=new Intent(HomeActivity.this,AboutActivity.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        backpress = (backpress + 1);
        Toast.makeText(HomeActivity.this, "Nhấn lại để thoát", Toast.LENGTH_SHORT).show();

        if (backpress>1) {
            finish();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (!backgroundMusic.isPlaying()){
            backgroundMusic.start();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        backgroundMusic.pause();
    }
}