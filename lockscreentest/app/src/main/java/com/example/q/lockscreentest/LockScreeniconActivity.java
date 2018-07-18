package com.example.q.lockscreentest;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

public class LockScreeniconActivity extends AppCompatActivity{

    private android.support.v7.app.ActionBar actionBar;
    ImageView inbtn, quitbtn;
    static int taskCount = 0;

    public static AppCompatActivity shouldfinish;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lockscreen_start);
        inbtn = (ImageView) findViewById(R.id.start);
        quitbtn= (ImageView)findViewById(R.id.quit);


        inbtn.bringToFront();
        IsTaskExist_Labeling isTaskExist_Labeling = new IsTaskExist_Labeling();
        isTaskExist_Labeling.execute("https://mymy.koreacentral.cloudapp.azure.com/api/image");

        IsTaskExist_Recording isTaskExist_recording = new IsTaskExist_Recording();
        isTaskExist_recording.execute("https://mymy.koreacentral.cloudapp.azure.com/api/textget");


        // FLAG_SHOW_WHEN_LOCKED : 안드로이드 기본 잠금보다 위에 Activity를 띄우는 것
        // FLAG_DISMISS_KEYGUARD : ?
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
                | WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);

        inbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LockScreeniconActivity.this, LockScreenActivity.class);
                startActivity(intent);
                finish();
            }
        });

        quitbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        shouldfinish = LockScreeniconActivity.this;


    }



}
