package com.haixl.ailatrieuphu.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.haixl.ailatrieuphu.R;

public class NoticeDialog extends Dialog implements View.OnClickListener {
    private Button btnYes;
    private Button btnNo;
    private TextView tvNotice;

    public NoticeDialog(@NonNull Context context) {
        super(context);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setCancelable(true);
        setContentView(R.layout.notice_dialog);

        btnNo = (Button) findViewById(R.id.btn_no);
        btnYes = (Button) findViewById(R.id.btn_yes);
        tvNotice = (TextView) findViewById(R.id.tv_notice);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
    }

    public void setNotification(String notification, String txtYes, String txtNo, View.OnClickListener onClickListener) {
        tvNotice.setText(notification);
        btnYes.setText(txtYes);
        btnNo.setText(txtNo);
        btnYes.setOnClickListener(onClickListener);
        btnNo.setOnClickListener(onClickListener);

        if (txtNo == null) btnNo.setVisibility(View.GONE);
        if (onClickListener == null) btnYes.setOnClickListener(this);

    }
    @Override
    public void onClick(View view) {

    }
}
