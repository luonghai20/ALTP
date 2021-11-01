package com.haixl.ailatrieuphu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.haixl.ailatrieuphu.dialog.AudienceDialog;
import com.haixl.ailatrieuphu.dialog.CallDialog;
import com.haixl.ailatrieuphu.dialog.NoticeDialog;
import com.haixl.ailatrieuphu.dialog.ScoreDialog;
import com.haixl.ailatrieuphu.maganer.DataBaseManager;
import com.haixl.ailatrieuphu.maganer.PlayMusic;
import com.haixl.ailatrieuphu.model.Question;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private TextView tvQuestion,tvA,tvB,tvC,tvD,tvTimer,tvMoney, tvLevel;
    private ImageView imgHelp5050,imgChangeQuestion,imgHelpCall,imgHelpAudience,imgStopGame;

    ArrayList<TextView> arrAnswer;
    ArrayList<String> arrMoney;

    private int level=1;
    private String trueAnswer;

    private static final long START_TIME_IN_MILLIS = 30000;
    private long mTimeLeftInMillis = START_TIME_IN_MILLIS;
    private CountDownTimer mCountDownTimer;

    private boolean help5050=true;
    private boolean helpChangeQuestion=true;
    private boolean helpCall=true;
    private boolean helpAudience=true;
    private boolean selected;
    private boolean isPlaying;
    private boolean Remuse;

    private CallDialog callDialog;
    private AudienceDialog audienceDialog;
    private NoticeDialog noticeDialog;
    private ScoreDialog scoreDialog;

    private Question questions;
    private DataBaseManager dataBaseManager;
    private PlayMusic musicAnswer,musicCount;
    private Handler handler;
    private View.OnClickListener listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findView();
        init();
        setClick();
        getNewQuestion();
        setQuestion();

        callDialog=new CallDialog(MainActivity.this);
        audienceDialog=new AudienceDialog(MainActivity.this);
        noticeDialog=new NoticeDialog(MainActivity.this);
        scoreDialog=new ScoreDialog(MainActivity.this);
    }
    public void findView(){
        tvQuestion=findViewById(R.id.txtCauHoi);
        tvA=findViewById(R.id.dapAn_A);
        tvB=findViewById(R.id.dapAn_B);
        tvC=findViewById(R.id.dapAn_C);
        tvD=findViewById(R.id.dapAn_D);
        tvMoney=findViewById(R.id.tvMoney);
        tvLevel =findViewById(R.id.tvLevel);
        tvTimer=findViewById(R.id.tvTimer);
        imgHelp5050=findViewById(R.id.help5050);
        imgChangeQuestion=findViewById(R.id.helpChangeQuestion);
        imgHelpCall=findViewById(R.id.helpCall);
        imgHelpAudience=findViewById(R.id.helpAudience);
        imgStopGame=findViewById(R.id.stopGame);
    }
    public void init(){
        arrAnswer=new ArrayList<>();
        arrAnswer.add(tvA);
        arrAnswer.add(tvB);
        arrAnswer.add(tvC);
        arrAnswer.add(tvD);

        arrMoney=new ArrayList<>();
        arrMoney.add("0");
        arrMoney.add("200");
        arrMoney.add("400");
        arrMoney.add("600");
        arrMoney.add("1,000");
        arrMoney.add("2,000");
        arrMoney.add("3,000");
        arrMoney.add("6,000");
        arrMoney.add("10,000");
        arrMoney.add("14,000");
        arrMoney.add("22,000");
        arrMoney.add("30,000");
        arrMoney.add("40,000");
        arrMoney.add("60,000");
        arrMoney.add("85,000");
        arrMoney.add("150,000");
    }
    public void setClick(){
        listener=new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkQuestion1(((TextView)view));
            }
        };
        for (TextView t:arrAnswer){
            t.setOnClickListener(listener);
        }
    }

    public void getNewQuestion(){
        dataBaseManager=new DataBaseManager(MainActivity.this);
        questions=dataBaseManager.getQuestion(level);
    }
    public void setQuestion(){
        musicAnswer =new PlayMusic(this);
        musicCount=new PlayMusic(this);
        getNewQuestion();
        tvQuestion.setText("Câu "+level+":"+"\n"+questions.getQuestion());
        tvA.setText(questions.getCaseA());
        tvB.setText(questions.getCaseB());
        tvC.setText(questions.getCaseC());
        tvD.setText(questions.getCaseD());
        switch (questions.getTrueCase()){
            case 1:
                trueAnswer=questions.getCaseA();
                break;
            case 2:
                trueAnswer=questions.getCaseB();
                break;
            case 3:
                trueAnswer=questions.getCaseC();
                break;
            case 4:
                trueAnswer=questions.getCaseD();
                break;
            default:
                trueAnswer="";
        }
        selected=false;
        tvLevel.setText("Level "+String.valueOf(level));
        tvMoney.setText(arrMoney.get(level-1));
        musicAnswer.AudioBackground(level);
        musicCount.AudioForQuestion(level);
        startTime();
        Remuse=false;

    }
    public void checkQuestion1(TextView view) {
        handler=new Handler();
        String selectedAnswer=view.getText().toString();
        view.setBackgroundResource(R.drawable.answer_background_selected);
        setClickUnable();
        selected=true;
        musicAnswer.AudioAnswerNow();
        pauseTime();

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                for (TextView t:arrAnswer){
                    String s=t.getText().toString();
                    if (s.equals(trueAnswer)){
                        t.setBackgroundResource(R.drawable.answer_background_true);
                        break;
                    }
                }
                if (selectedAnswer.equals(trueAnswer)){
                    musicAnswer.AudioForCorrectAnswers(questions.getTrueCase());
                    level++;
                    if (level>15){
                        gameWon();
                        level=15;
                    }
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            musicAnswer.stopMusic();
                            setQuestion();
                            setClickAble();
                        }
                    },4000);
                }else{
                    musicAnswer.AudioForWrongAnswers(questions.getTrueCase());
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            saveScore();
                        }
                    },4000);
                }
            }
        },3000);
    }

    public void setClickAble(){
        for (int i=0;i<arrAnswer.size();i++){
            arrAnswer.get(i).setOnClickListener(listener);
            arrAnswer.get(i).setVisibility(View.VISIBLE);
            arrAnswer.get(i).setBackgroundResource(R.drawable.answer_background_normal);
        }
        if (helpAudience){
            imgHelpAudience.setClickable(true);
        }
        if (helpCall){
            imgHelpCall.setClickable(true);
        }
        if (help5050){
            imgHelp5050.setClickable(true);
        }
        if (helpChangeQuestion){
            imgChangeQuestion.setClickable(true);
        }
        imgStopGame.setClickable(true);
    }
    public void setClickUnable(){
        for (int i=0;i<arrAnswer.size();i++){
            arrAnswer.get(i).setOnClickListener(null);
        }
        pauseTime();
        if (helpAudience){
            imgHelpAudience.setClickable(false);
        }
        if (helpCall){
            imgHelpCall.setClickable(false);
        }
        if (help5050){
            imgHelp5050.setClickable(false);
        }
        if (helpChangeQuestion){
            imgChangeQuestion.setClickable(false);
        }
        imgStopGame.setClickable(false);

    }

    public void startTime(){
        mTimeLeftInMillis=START_TIME_IN_MILLIS;
        mCountDownTimer=new CountDownTimer(mTimeLeftInMillis,1000) {
            @Override
            public void onTick(long mleftTime) {
                mTimeLeftInMillis=mleftTime;
                int second=(int) (mTimeLeftInMillis/1000);
                tvTimer.setText(String.valueOf(second));
            }
            @Override
            public void onFinish() {
                saveScore();
            }
        };
        mCountDownTimer.start();
        isPlaying=true;
    }
    public void pauseTime(){
        mCountDownTimer.cancel();
        isPlaying=false;
    }
    public void resumeTime(){
        mCountDownTimer=new CountDownTimer(mTimeLeftInMillis,1000) {
            @Override
            public void onTick(long mleftTime) {
                mTimeLeftInMillis=mleftTime;
                int second=(int) (mTimeLeftInMillis/1000);
                tvTimer.setText(String.valueOf(second));
            }
            @Override
            public void onFinish() {
                saveScore();
            }
        };
        mCountDownTimer.start();
        isPlaying=true;
    }

    public void help5050(View view) {
        if (help5050==false){
            return;
        }
        Random r=new Random();
        int soDapAnBoDi=2;
        do {
            int viTriDapAnAn=r.nextInt(4);
            TextView tv=arrAnswer.get(viTriDapAnAn);
            if (tv.getVisibility()==View.VISIBLE && tv.getText().toString().equals(trueAnswer)==false){
                tv.setVisibility(View.INVISIBLE);
                tv.setOnClickListener(null);
                soDapAnBoDi--;
            }

        }while (soDapAnBoDi>0);
        help5050=false;
        imgHelp5050.setOnClickListener(null);
        imgHelp5050.setImageResource(R.drawable.help_5050_x);
    }
    public void helpChangeQuestion(View view) {
        if (helpChangeQuestion==false){
            return;
        }
        noticeDialog.setCancelable(false);
        noticeDialog.setNotification("Bạn thực sự muốn đổi câu hỏi?", "Đồng ý", "Huỷ bỏ", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (view.getId()==R.id.btn_yes){
                    getNewQuestion();
                    tvQuestion.setText("Câu "+level+":"+"\n"+questions.getQuestion());
                    tvA.setText(questions.getCaseA());
                    tvB.setText(questions.getCaseB());
                    tvC.setText(questions.getCaseC());
                    tvD.setText(questions.getCaseD());
                    switch (questions.getTrueCase()){
                        case 1:
                            trueAnswer=questions.getCaseA();
                            break;
                        case 2:
                            trueAnswer=questions.getCaseB();
                            break;
                        case 3:
                            trueAnswer=questions.getCaseC();
                            break;
                        case 4:
                            trueAnswer=questions.getCaseD();
                            break;
                        default:
                            trueAnswer="";
                    }
                    helpChangeQuestion=false;
                    imgChangeQuestion.setOnClickListener(null);
                    imgChangeQuestion.setImageResource(R.drawable.help_change_question_x);
                    setClickAble();
                }
                noticeDialog.dismiss();
            }
        });
        noticeDialog.show();

    }
    public void stopGame(View view) {
        pauseTime();
        noticeDialog.setCancelable(false);
        noticeDialog.setNotification("Bạn thực sự muốn dừng cuộc chơi ?", "Đồng ý", "Hủy bỏ", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getId() == R.id.btn_yes) {
                    saveScore();
                }else resumeTime();
                noticeDialog.dismiss();
            }
        });
        noticeDialog.show();
    }
    public void helpCall(View view) {
        if (helpCall==false){
            return;
        }
        pauseTime();
        callDialog.setTrueAnswer(questions.getTrueCase());
        callDialog.show();
        callDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                imgHelpCall.setOnClickListener(null);
                imgHelpCall.setImageResource(R.drawable.help_call_x);
                resumeTime();
            }
        });
        helpCall=false;
    }
    public void helpAudience(View view) {
        if (helpAudience==false){
            return;
        }
        pauseTime();
        audienceDialog.setBarVote(questions.getTrueCase());
        audienceDialog.show();
        audienceDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                imgHelpAudience.setOnClickListener(null);
                imgHelpAudience.setImageResource(R.drawable.help_audience_x);
                resumeTime();
            }
        });
        helpAudience=false;
    }

    public void saveScore(){
        if (level==1){
            gameOver();
        }else{
            scoreDialog.setCancelable(false);
            scoreDialog.setScore(arrMoney.get(level-1));
            scoreDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialogInterface) {
                    musicAnswer.stopMusic();
                    finish();
                }
            });
            scoreDialog.show();
        }
    }

    public void gameOver(){
        handler =new Handler();
        Toast.makeText(MainActivity.this,"GAME OVER",Toast.LENGTH_SHORT).show();
        musicAnswer.stopMusic();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                finish();
            }
        },3000);
    }
    public void gameWon(){
        Toast.makeText(MainActivity.this,"YOU WIN",Toast.LENGTH_SHORT).show();
        new CountDownTimer(1000, 100) {
            @Override
            public void onTick(long l) {

            }

            @Override
            public void onFinish() {
                saveScore();
            }
        }.start();
    }

    @Override
    protected void onPause() {
        musicAnswer.stopMusic();
        if (isPlaying){
            pauseTime();
            Remuse=true;
        }
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        super.onRestart();
        if (!isPlaying && Remuse){
            resumeTime();
            Remuse=false;
        }
    }

    @Override
    public void onBackPressed() {
        if (selected){
            return;
        }else{
            noticeDialog.setCancelable(false);
            noticeDialog.setNotification("Bạn thực sự muốn dừng cuộc chơi ?", "Đồng ý", "Hủy bỏ", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (v.getId() == R.id.btn_yes) {
                        saveScore();
                    }
                    noticeDialog.dismiss();
                }
            });
            noticeDialog.show();
        }
    }


}