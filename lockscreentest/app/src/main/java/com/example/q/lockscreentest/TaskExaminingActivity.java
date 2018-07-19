package com.example.q.lockscreentest;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import me.imid.swipebacklayout.lib.SwipeBackLayout;
import me.imid.swipebacklayout.lib.app.SwipeBackActivity;


public class TaskExaminingActivity extends AppCompatActivity {

    Button right_btn, wrong_btn;

    Intent intent;
    byte[] img_binary;
    int x1, x2, x3, x4;

    public static AppCompatActivity examiningActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.task_examining);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
                | WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);

        TextView tv = (TextView) findViewById(R.id.examiningtitle);
        tv.setText("제대로 라벨링 되었는지 확인해주세요.");

        intent = getIntent();
        img_binary = intent.getByteArrayExtra("img_binary");
        x1 = Integer.parseInt(intent.getStringExtra("x1"));
        x2 = Integer.parseInt(intent.getStringExtra("x2"));
        x3 = Integer.parseInt(intent.getStringExtra("x3"));
        x4 = Integer.parseInt(intent.getStringExtra("x4"));

        System.out.println("x1 : "+x1+"x2 : "+x2+"x3 : "+x3+"x4 : "+x4);

        ImageView iv = (ImageView) findViewById(R.id.examiningimage);
        iv.setImageBitmap(Post_ExaminingTaskHttp.result_e);

        ImageView rect =(ImageView)findViewById(R.id.rect);
        ConstraintLayout.LayoutParams mLayoutParams =
                (ConstraintLayout.LayoutParams) rect.getLayoutParams();

        mLayoutParams.topMargin = (x1+10);
        mLayoutParams.leftMargin = (x3+10);
        mLayoutParams.height = x2-x1;
        mLayoutParams.width = x4-x3;

        rect.setLayoutParams(mLayoutParams);
        rect.bringToFront();

        right_btn = findViewById(R.id.right_btn);
        wrong_btn = findViewById(R.id.wrong_btn);

        right_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "전송 완료. 라벨링이 잘된 사진이군요!", Toast.LENGTH_LONG).show();
                Post_ExaminingTaskHttp post_ExaminingTaskHttp = new Post_ExaminingTaskHttp(TaskExaminingActivity.this);
                post_ExaminingTaskHttp.execute("https://mymy.koreacentral.cloudapp.azure.com/api/checkimage");
            }
        });

        wrong_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "전송 완료. 다시 라벨링 해봐야 겠네요...", Toast.LENGTH_LONG).show();
                Post_ExaminingTaskHttp post_ExaminingTaskHttp = new Post_ExaminingTaskHttp(TaskExaminingActivity.this);
                post_ExaminingTaskHttp.execute("https://mymy.koreacentral.cloudapp.azure.com/api/checkimage");
            }
        });



        examiningActivity = TaskExaminingActivity.this;

    }

}
