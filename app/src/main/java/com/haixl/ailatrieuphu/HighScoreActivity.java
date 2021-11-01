package com.haixl.ailatrieuphu;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import com.haixl.ailatrieuphu.adapter.highScoreAdapter;
import com.haixl.ailatrieuphu.maganer.DataBaseManager;
import com.haixl.ailatrieuphu.model.highScore;

import java.util.ArrayList;

public class HighScoreActivity extends AppCompatActivity {
    ListView lvHighScore;
    ArrayList<highScore> highScores;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_high_score);

        lvHighScore=findViewById(R.id.listHighScore);

        DataBaseManager dataBaseManager=new DataBaseManager(HighScoreActivity.this);
        highScores=new ArrayList<>();
        highScores=dataBaseManager.getHighScore();

        highScoreAdapter highScoreAdapter=new highScoreAdapter(HighScoreActivity.this,highScores);
        lvHighScore.setAdapter(highScoreAdapter);
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}