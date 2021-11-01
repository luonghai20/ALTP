package com.haixl.ailatrieuphu.maganer;

import android.content.Context;
import android.media.MediaPlayer;

import com.haixl.ailatrieuphu.R;

import java.util.Random;

public class PlayMusic{
    private Context context;
    private MediaPlayer mediaPlayer;
    Random random;
    public static final int[] TRUE_A = {R.raw.true_a, R.raw.true_a2};
    public static final int[] TRUE_B = {R.raw.true_b, R.raw.true_b2, R.raw.true_b3};
    public static final int[] TRUE_C = {R.raw.true_c, R.raw.true_c2, R.raw.true_c3};
    public static final int[] TRUE_D = {R.raw.true_d2, R.raw.true_d3};
    public static final int[] ANS_NOW = {R.raw.ans_now1, R.raw.ans_now2, R.raw.ans_now3};


    public PlayMusic(Context context) {
        this.context = context;
    }
    public void AudioForCorrectAnswers(int trueCase){
        random=new Random();
        switch (trueCase){
            case 1:
                int a=TRUE_A[random.nextInt(1)];
                playMusic(a);
                break;
            case 2:
                int b = TRUE_B[random.nextInt(2)];
                playMusic(b);
                break;
            case 3:
                int c = TRUE_C[random.nextInt(2)];
                playMusic(c);
                break;
            case 4:
                int d = TRUE_D[random.nextInt(1)];
                playMusic(d);
                break;
        }
    }
    public void AudioForWrongAnswers(int trueCase){
        switch (trueCase){
            case 1:
                int a=R.raw.lose_a;
                playMusic(a);
                break;
            case 2:
                int b=R.raw.lose_b;
                playMusic(b);
                break;
            case 3:
                int c=R.raw.lose_c;
                playMusic(c);
                break;
            case 4:
                int d=R.raw.lose_d;
                playMusic(d);
                break;
        }
    }
    public void AudioBackground(int level){
        if (level<=5){
            int bgMusic=R.raw.background_question_music_1;
            playMusic(bgMusic);
        }else if (level<=10){
            int bgMusic=R.raw.background_question_music_2;
            playMusic(bgMusic);
        }else{
            int bgMusic=R.raw.background_question_music_3;
            playMusic(bgMusic);
        }
    }
    public void AudioForQuestion(int level){
        switch (level){
            case 1:
                int bgQuestion1=R.raw.ques1;
                playMusic(bgQuestion1);
                break;
            case 2:
                int bgQuestion2=R.raw.ques2;
                playMusic(bgQuestion2);
                break;
            case 3:
                int bgQuestion3=R.raw.ques3;
                playMusic(bgQuestion3);
                break;
            case 4:
                int bgQuestion4=R.raw.ques4;
                playMusic(bgQuestion4);
                break;
            case 5:
                int bgQuestion5=R.raw.ques5;
                playMusic(bgQuestion5);
                break;
            case 6:
                int bgQuestion6=R.raw.ques6;
                playMusic(bgQuestion6);
                break;
            case 7:
                int bgQuestion7=R.raw.ques7;
                playMusic(bgQuestion7);
                break;
            case 8:
                int bgQuestion8=R.raw.ques8;
                playMusic(bgQuestion8);
                break;
            case 9:
                int bgQuestion9=R.raw.ques9;
                playMusic(bgQuestion9);
                break;
            case 10:
                int bgQuestion10=R.raw.ques10;
                playMusic(bgQuestion10);
                break;
            case 11:
                int bgQuestion11=R.raw.ques11;
                playMusic(bgQuestion11);
                break;
            case 12:
                int bgQuestion12=R.raw.ques12;
                playMusic(bgQuestion12);
                break;
            case 13:
                int bgQuestion13=R.raw.ques13;
                playMusic(bgQuestion13);
                break;
            case 14:
                int bgQuestion14=R.raw.ques14;
                playMusic(bgQuestion14);
                break;
            case 15:
                int bgQuestion15=R.raw.ques15;
                playMusic(bgQuestion15);
                break;
        }
    }
    public void AudioAnswerNow(){
        random=new Random();
        playMusic(ANS_NOW[random.nextInt(2)]);
    }


    public void playMusic(int audiofile){
        stopMusic();
        mediaPlayer=new MediaPlayer();
        mediaPlayer=MediaPlayer.create(context,audiofile);
        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                mediaPlayer.start();
            }
        });

        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                mediaPlayer.release();
            }
        });
    }
    public void stopMusic() {
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer=null;
        }
    }
}
