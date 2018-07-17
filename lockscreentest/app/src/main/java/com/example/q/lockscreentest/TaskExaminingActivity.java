package com.example.q.lockscreentest;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import me.imid.swipebacklayout.lib.SwipeBackLayout;
import me.imid.swipebacklayout.lib.app.SwipeBackActivity;


public class TaskExaminingActivity extends SwipeBackActivity {

    ImageView mImageView;
    ViewGroup mRoot;
    private int mXDelta;
    private int mYDelta;
    boolean sizeFlag;
    Rect rect;

    Intent intent;
    byte[] img_binary;

    private SwipeBackLayout mSwipeBackLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.task_labeling);

        mSwipeBackLayout = getSwipeBackLayout();

        mSwipeBackLayout.setEdgeTrackingEnabled(SwipeBackLayout.EDGE_LEFT | SwipeBackLayout.EDGE_RIGHT);
        TextView tv = (TextView) findViewById(R.id.labelingtitle);
        tv.setText("마 제대로했는지 검사해라");

        intent = getIntent();
        img_binary = intent.getByteArrayExtra("img_binary");

        ImageView iv = (ImageView) findViewById(R.id.labelingimage);
        iv.setImageBitmap(Post_ExaminingTaskHttp.result_e);


        mSwipeBackLayout.addSwipeListener(new SwipeBackLayout.SwipeListener() {
            @Override
            public void onScrollStateChange(int state, float scrollPercent) {
                // 스크롤 될 때
            }

            @Override
            public void onEdgeTouch(int edgeFlag) {
                // 설정된 모서리를 터치 했을 때
            }
            @Override
            public void onScrollOverThreshold() {
                finish();
                // 창이 닫힐 정도로 스와이프 되었을 때
            }
        });

    }

}
