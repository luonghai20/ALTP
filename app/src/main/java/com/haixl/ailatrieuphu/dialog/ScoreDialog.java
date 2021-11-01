package com.haixl.ailatrieuphu.dialog;

import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.haixl.ailatrieuphu.R;
import com.haixl.ailatrieuphu.maganer.DataBaseManager;

public class ScoreDialog extends Dialog implements View.OnClickListener{
    private int score;
    private EditText edName;
    private TextView tvMoney;
    public ScoreDialog(@NonNull Context context) {
        super(context);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.score_dialog);
        getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        edName = (EditText) findViewById(R.id.edName);
        tvMoney = (TextView) findViewById(R.id.tvMoney);
        findViewById(R.id.btnYes).setOnClickListener(this);
    }
    public void setScore(String score) {
        tvMoney.setText(score+" VND");
        this.score = Integer.parseInt(score.replaceAll(",", ""));
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btnYes){
            if (edName.getText().toString().isEmpty()) {
                return;
            }
            DataBaseManager databaseManager = new DataBaseManager(super.getContext());
            ContentValues values = new ContentValues();
            values.put("Name", edName.getText().toString().trim());
            values.put("Score", score);
            databaseManager.insert("HighScore", values);
            dismiss();
        }
    }
}
