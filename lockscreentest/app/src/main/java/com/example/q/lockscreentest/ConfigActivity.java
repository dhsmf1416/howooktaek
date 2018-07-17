package com.example.q.lockscreentest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;


//일종의 설정화면
public class ConfigActivity extends AppCompatActivity {

    private Button onBtn, offBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.config_activity);

        onBtn = (Button) findViewById(R.id.btn1);
        offBtn = (Button) findViewById(R.id.btn2);

        onBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ConfigActivity.this, ScreenService.class);
                startService(intent);
            }
        });

        offBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ConfigActivity.this, ScreenService.class);
                stopService(intent);
            }
        });

    }

}

