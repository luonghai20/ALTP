package com.haixl.ailatrieuphu.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.haixl.ailatrieuphu.R;

import java.util.Random;

public class AudienceDialog extends Dialog{
    Random random;
    private TextView percent[];
    private TextView barVote[];
    private int indexTrue, index1, index2, index3;
    private int percentTrue,percentFalse1,percentFalse2,percentFalse3;

    public AudienceDialog(Context context) {
        super(context);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setCanceledOnTouchOutside(false);
        setCancelable(false);
        setContentView(R.layout.audience_layout);

        percent =new TextView[4];
        percent[0]=findViewById(R.id.tv_pesent_1);
        percent[1]=findViewById(R.id.tv_pesent_2);
        percent[2]=findViewById(R.id.tv_pesent_3);
        percent[3]=findViewById(R.id.tv_pesent_4);

        barVote=new TextView[4];
        barVote[0]=findViewById(R.id.bar_1);
        barVote[1]=findViewById(R.id.bar_2);
        barVote[2]=findViewById(R.id.bar_3);
        barVote[3]=findViewById(R.id.bar_4);

        findViewById(R.id.btn_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

    public void setPercent(){
        random=new Random();
        int total=100;
        percentTrue=(random.nextInt(50)+50);
        total-=percentTrue;

        percentFalse1=random.nextInt(total);
        total-=percentFalse1;

        percentFalse2=random.nextInt(total);
        total-=percentFalse2;

        percentFalse3=total;
    }

    public void setBarVote(int trueCase){
        setPercent();
        random=new Random();
        indexTrue=trueCase;
        do {
            index1= random.nextInt(4)+1;
        }while (index1==indexTrue);

        do {
            index2= random.nextInt(4)+1;
        }while (index2==indexTrue || index2==index1);

        do {
            index3= random.nextInt(4)+1;
        }while (index3==indexTrue || index3==index2 || index3==index1);

        final ViewGroup.LayoutParams layoutParams = barVote[indexTrue-1].getLayoutParams();
        final ViewGroup.LayoutParams layoutParams1 = barVote[index1-1].getLayoutParams();
        final ViewGroup.LayoutParams layoutParams2 = barVote[index2-1].getLayoutParams();
        final ViewGroup.LayoutParams layoutParams3 = barVote[index3-1].getLayoutParams();

        layoutParams.height=percentTrue*3;
        layoutParams1.height=percentFalse1*3;
        layoutParams2.height=percentFalse2*3;
        layoutParams3.height=percentFalse3*3;

        percent[indexTrue-1].setText(String.valueOf(percentTrue));
        percent[index1-1].setText(String.valueOf(percentFalse1));
        percent[index2-1].setText(String.valueOf(percentFalse2));
        percent[index3-1].setText(String.valueOf(percentFalse3));
    }


}
