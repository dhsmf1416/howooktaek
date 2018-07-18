package com.example.q.lockscreentest;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


//일종의 설정화면
public class ConfigActivity extends AppCompatActivity {

    private Button onBtn, offBtn, inBtn;
    private TextView configState;
    private boolean state = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.config_activity);

        onBtn = (Button) findViewById(R.id.btn1);
        offBtn = (Button) findViewById(R.id.btn2);
        inBtn = (Button) findViewById(R.id.btn3);
        configState = (TextView) findViewById(R.id.configState);

        if (isServiceRunningCheck())
            configState.setText("현재 상태는 : on");
        else
            configState.setText("현재 상태는 : off");


        onBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ConfigActivity.this, ScreenService.class);
                configState.setText("현재 상태는 : on");
                state = true;
                startService(intent);

            }
        });

        offBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ConfigActivity.this, ScreenService.class);
                configState.setText("현재 상태는 : off");
                state = false;
                stopService(intent);
            }
        });

        inBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ConfigActivity.this, LockScreenActivity.class);
                startActivity(intent);
            }

        });


    }

    public boolean isServiceRunningCheck() {
        ActivityManager manager = (ActivityManager) this.getSystemService(Activity.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            System.out.println(service.service.getClassName());

            if ("com.example.q.lockscreentest.ScreenService".equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }


}

