package com.haixl.ailatrieuphu.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.haixl.ailatrieuphu.R;
import com.haixl.ailatrieuphu.model.highScore;

import java.util.ArrayList;

public class highScoreAdapter extends BaseAdapter {
    Context context;
    ArrayList<highScore> highScores;

    public highScoreAdapter(Context context, ArrayList<highScore> highScores) {
        this.context = context;
        this.highScores = highScores;
    }


    @Override
    public int getCount() {
        if (highScores==null){
            return 0;
        }else{
            return highScores.size();
        }
    }

    @Override
    public Object getItem(int i) {
        return highScores.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater=LayoutInflater.from(context);
        view=inflater.inflate(R.layout.item_list_highscore,viewGroup,false);

        TextView txtRank=view.findViewById(R.id.tv_rank);
        TextView txtName=view.findViewById(R.id.tv_name);
        TextView txtScore=view.findViewById(R.id.tv_score);

        txtRank.setText(String.valueOf(i+1));
        txtName.setText(highScores.get(i).getName());
        txtScore.setText(String.valueOf(highScores.get(i).getScore()));

        return view;
    }
}
