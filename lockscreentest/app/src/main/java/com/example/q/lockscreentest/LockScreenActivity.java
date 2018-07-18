package com.example.q.lockscreentest;

import android.content.Intent;
import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class LockScreenActivity extends AppCompatActivity {

    private ListView lv_main;
    private android.support.v7.app.ActionBar actionBar;
    final static ArrayList<TaskListItem> mTaskList = new ArrayList<TaskListItem>();
    static ListviewAdapter adapter;
    public TaskListItem getItem(int position){ return LockScreenActivity.mTaskList.get(position); }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lockscreen_activity);
        mTaskList.clear();

        // FLAG_SHOW_WHEN_LOCKED : 안드로이드 기본 잠금보다 위에 Activity를 띄우는 것
        // FLAG_DISMISS_KEYGUARD : ?
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
                | WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);

        // adapter 연결
        lv_main = (ListView) findViewById(R.id.taskList);
        TextView tv = findViewById(R.id.possibleTask);
        tv.setText("진행 가능한 작업");

        adapter = new ListviewAdapter(this, R.layout.task_lv, LockScreenActivity.mTaskList);
        lv_main.setAdapter(adapter);

        actionBar = getSupportActionBar();

        PostHttp_Labeling postHttp_labeling = new PostHttp_Labeling(this);
        postHttp_labeling.execute("https://mymy.koreacentral.cloudapp.azure.com/api/image");

        PostHttp_Recording postHttp_recording = new PostHttp_Recording(this);
        postHttp_recording.execute("https://mymy.koreacentral.cloudapp.azure.com/api/textget");

        lv_main.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

                int id_int = (int) id;
                System.out.println("아이디"+id_int);
                switch (id_int) {
                    case 1000:
                        Post_LabelingTaskHttp post_LabelingTaskHttp = new Post_LabelingTaskHttp(LockScreenActivity.this);
                        post_LabelingTaskHttp.execute("https://mymy.koreacentral.cloudapp.azure.com/api/image");
                        break;
                    case 1001:
                        Post_ExaminingTaskHttp post_examiningTaskHttp = new Post_ExaminingTaskHttp(LockScreenActivity.this);
                        //postLabelingTaskHttp.execute("https://mymy.koreacentral.cloudapp.azure.com/api/image");
                        break;
                    case 1002:
                        Post_RecordingTaskHttp post_recordingTaskHttp = new Post_RecordingTaskHttp(LockScreenActivity.this);
                        post_recordingTaskHttp.execute("https://mymy.koreacentral.cloudapp.azure.com/api/textget");
                        break;

                }
            }
        });




        ////////////////////////////////////
        /* 버튼을 누르면 pip모드로 들어감 */
        ////////////////////////////////////

//        Button button = (Button) findViewById(R.id.testbutton);
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//
//                    getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
//                            | WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
//
//                    //기본 디스플레이의 사이즈를 가지고 오는 행위
//                    Display display = getWindowManager().getDefaultDisplay();
//                    Point size = new Point();
//                    display.getSize(size);
//                    int width = size.x;
//                    int height = size.y;
//
//                    Rational aspextRatio = new Rational(width, height);
//                    PictureInPictureParams.Builder mPictureinPictureParamsBuilder =
//                            new PictureInPictureParams.Builder();
//
//                    mPictureinPictureParamsBuilder.setAspectRatio(aspextRatio).build();
//
//                    enterPictureInPictureMode(mPictureinPictureParamsBuilder.build());
//                }
//            }
//        });


        //테스트를 위한 액티비티 변경 버튼
        Button test_btn = (Button) findViewById(R.id.testbutton);
        test_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LockScreenActivity.this, LockScreeniconActivity.class);
                startActivity(intent);
            }
        });


    }



    @Override
    public void onPictureInPictureModeChanged(boolean isInPictureInPictureMode, Configuration newConfig) {
        if (isInPictureInPictureMode) {
            actionBar.hide();
        } else {
            actionBar.show();
        }
    }

}
