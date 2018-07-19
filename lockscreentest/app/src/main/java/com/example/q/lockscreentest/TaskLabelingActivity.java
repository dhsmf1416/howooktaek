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

import org.json.JSONException;
import org.json.JSONObject;

public class TaskLabelingActivity extends AppCompatActivity{

    ImageView mImageView;
//    ViewGroup mRoot;
    private int mXDelta;
    static String myID= "";
    private int mYDelta;
    boolean sizeFlag;
    Rect rect;
    int hori1;
    int hori2;
    int vert1;
    int vert2;
    ViewGroup mRoot;
    View horizontal1;
    View horizontal2;
    View vertical1;
    View vertical2;
    Intent intent;
    byte[] img_binary;
    Button submitButton;

    public static AppCompatActivity labelingActivity;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        rect = new Rect();
        setContentView(R.layout.task_labeling);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
                | WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);

//        mRoot = (RelativeLayout) findViewById (R.id.labelingimage_layout);
        //mImageView = (ImageView) findViewById(R.id.croprect);
        //mImageView.setOnTouchListener(this);
        //mImageView.bringToFront();


        TextView tv = findViewById(R.id.labelingtitle);
        tv.setText("자동차를 라벨링 해주세요.");

        intent = getIntent();
        img_binary = intent.getByteArrayExtra("img_binary");
        submitButton = findViewById(R.id.submit);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PostHttp_Labeling_submit php = new PostHttp_Labeling_submit(TaskLabelingActivity.this);
                try {
                    JSONObject jsonParams = new JSONObject();
                    jsonParams.put("x1", hori1);
                    jsonParams.put("x2",hori2);
                    jsonParams.put("x3",vert1);
                    jsonParams.put("x4",vert2);
                    jsonParams.put("id",myID);
                    php.execute("https://mymy.koreacentral.cloudapp.azure.com/api/imagesubmit", jsonParams);

                    Toast.makeText(getApplicationContext(), "라벨링 데이터 전송에 성공하였습니다.", Toast.LENGTH_LONG).show();
                    Post_LabelingTaskHttp post_labelingTaskHttp = new Post_LabelingTaskHttp(TaskLabelingActivity.this);
                    post_labelingTaskHttp.execute("https://mymy.koreacentral.cloudapp.azure.com/api/image");
                }catch (JSONException e)
                {
                    e.printStackTrace();
                }
            }
        });

        ImageView iv = findViewById(R.id.labelingimage);
        iv.setImageBitmap(Post_LabelingTaskHttp.result_d);
        System.out.println(iv.getHeight());
        mRoot = (ConstraintLayout)findViewById(R.id.labelingimage_layout);
        horizontal1 = findViewById(R.id.horizontal1);
        ConstraintLayout.LayoutParams xx = (ConstraintLayout.LayoutParams)horizontal1.getLayoutParams();
        hori1 = xx.topMargin;
        horizontal1.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                final int y = (int) event.getRawY();
                switch(event.getAction() & MotionEvent.ACTION_MASK) {
                    case MotionEvent.ACTION_DOWN:
                        ConstraintLayout.LayoutParams IParams = (ConstraintLayout.LayoutParams) horizontal1.getLayoutParams();
                        mYDelta = y - IParams.topMargin;
                        break;
                    case MotionEvent.ACTION_MOVE:
                        ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) horizontal1.getLayoutParams();
                        if(layoutParams.topMargin > hori2 - 100 && layoutParams.topMargin < y - mYDelta)
                            return true;
                        layoutParams.topMargin = y - mYDelta;
                        hori1 = layoutParams.topMargin;
                        horizontal1.setLayoutParams(layoutParams);
                        break;
                    default:
                        break;
                }
                mRoot.invalidate();
                return true;
            }
        });
        horizontal2 = findViewById(R.id.horizontal2);
        ConstraintLayout.LayoutParams xx2 = (ConstraintLayout.LayoutParams)horizontal2.getLayoutParams();
        hori2 = xx2.topMargin;
        horizontal2.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                final int y = (int) event.getRawY();
                switch(event.getAction() & MotionEvent.ACTION_MASK) {
                    case MotionEvent.ACTION_DOWN:
                        ConstraintLayout.LayoutParams IParams = (ConstraintLayout.LayoutParams) horizontal2.getLayoutParams();
                        mYDelta = y - IParams.topMargin;
                        break;
                    case MotionEvent.ACTION_MOVE:
                        ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) horizontal2.getLayoutParams();
                        hori2 = layoutParams.topMargin;
                        if(layoutParams.topMargin < hori1 + 100 && layoutParams.topMargin > y - mYDelta)
                            return true;

                        layoutParams.topMargin = y - mYDelta;

                        horizontal2.setLayoutParams(layoutParams);
                        break;
                    default:
                        break;
                }
                mRoot.invalidate();
                return true;
            }
        });
        vertical1 = findViewById(R.id.vertical1);
        ConstraintLayout.LayoutParams xx3 = (ConstraintLayout.LayoutParams)vertical1.getLayoutParams();
        vert1 = xx3.leftMargin;
        vertical1.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                final int x = (int) event.getRawX();
                switch(event.getAction() & MotionEvent.ACTION_MASK) {
                    case MotionEvent.ACTION_DOWN:
                        ConstraintLayout.LayoutParams IParams = (ConstraintLayout.LayoutParams) vertical1.getLayoutParams();
                        mXDelta = x - IParams.leftMargin;
                        break;
                    case MotionEvent.ACTION_MOVE:
                        ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) vertical1.getLayoutParams();
                        vert1 = layoutParams.leftMargin;
                        if(layoutParams.leftMargin > vert2 - 100 && layoutParams.leftMargin < x - mXDelta)
                            return true;
                        layoutParams.leftMargin = x - mXDelta;
                        vertical1.setLayoutParams(layoutParams);
                        break;
                    default:
                        break;
                }
                mRoot.invalidate();
                return true;
            }
        });
        vertical2 = findViewById(R.id.vertical2);
        ConstraintLayout.LayoutParams xx4 = (ConstraintLayout.LayoutParams)vertical2.getLayoutParams();
        vert2 = xx4.leftMargin;
        vertical2.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                final int x = (int) event.getRawX();
                switch(event.getAction() & MotionEvent.ACTION_MASK) {
                    case MotionEvent.ACTION_DOWN:
                        ConstraintLayout.LayoutParams IParams = (ConstraintLayout.LayoutParams) vertical2.getLayoutParams();
                        mXDelta = x - IParams.leftMargin;
                        break;
                    case MotionEvent.ACTION_MOVE:
                        ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) vertical2.getLayoutParams();
                        vert2 = layoutParams.leftMargin;
                        if(layoutParams.leftMargin < vert1 + 100 && layoutParams.leftMargin > x - mXDelta)
                            return true;
                        layoutParams.leftMargin = x - mXDelta;
                        vertical2.setLayoutParams(layoutParams);
                        break;
                    default:
                        break;
                }
                mRoot.invalidate();
                return true;
            }
        });

        labelingActivity = TaskLabelingActivity.this;
    }

//    @Override
//    public boolean onTouch(View v, MotionEvent event) {
//
//        final int x = (int) event.getRawX();
//        final int y = (int) event.getRawY();
//        switch (event.getAction() & MotionEvent.ACTION_MASK) {
//            case MotionEvent.ACTION_DOWN:
//                sizeFlag = false;
//                RelativeLayout.LayoutParams lParams = (RelativeLayout.LayoutParams) mImageView.getLayoutParams();
//                mXDelta = x - lParams.leftMargin;
//                mYDelta = y - lParams.topMargin;
//                break;
//            case MotionEvent.ACTION_UP:
//            case MotionEvent.ACTION_POINTER_DOWN:
//            case MotionEvent.ACTION_POINTER_UP:
//                break;
//            case MotionEvent.ACTION_MOVE:
//                v.getGlobalVisibleRect(rect);
//
//                RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) mImageView.getLayoutParams();
//                String touchpoint = "";
//
//                if(Math.abs(rect.bottom - y) < 80 && Math.abs(rect.right - x) < 80)
//                    touchpoint = "rightbottom";
//                else if(Math.abs(rect.right - x) < 80 && Math.abs(rect.top - y) < 80)
//                    touchpoint = "righttop";
//                else if(Math.abs(rect.top - y) < 80 && Math.abs(rect.left - x) < 80)
//                    touchpoint = "lefttop";
//                else if(Math.abs(rect.left - x) < 80 && Math.abs(rect.right - y) < 80)
//                    touchpoint = "leftbottom";
//                else if(Math.abs(rect.bottom - y) < 80 )
//                    touchpoint = "bottom";
//                else if(Math.abs(rect.right - x) < 80 )
//                    touchpoint = "right";
//                else if(Math.abs(rect.top - y) < 80 )
//                    touchpoint = "top";
//                else if(Math.abs(rect.left - x) < 80 )
//                    touchpoint = "left";
//                else
//                    touchpoint = "";
//
//
////                System.out.println("오른쪽 마진 : " + layoutParams.rightMargin);
////                System.out.println("왼쪽 마진 : " + layoutParams.leftMargin);
////                System.out.println("위쪽 마진 : " + layoutParams.topMargin);
////                System.out.println("아래쪽 마진 : " + layoutParams.bottomMargin);
////                System.out.println(touchpoint);
//
//
//                switch (touchpoint){
//
//                    case "rightbottom":
//                        sizeFlag = true;
//                        if(layoutParams.width > 200 || (x - rect.right) > 0)
//                            layoutParams.width += x - rect.right;
//                        if(layoutParams.height > 200 || (y - rect.bottom) > 0)
//                            layoutParams.height += y - rect.bottom;
//                        mImageView.setLayoutParams(layoutParams);
//                        break;
////
////                    case "righttop":
////                        sizeFlag = true;
////                        if(layoutParams.width > 200 || (x - rect.right) > 0)
////                            layoutParams.width += x - rect.right;
////                        if(layoutParams.height > 200 || -(y- rect.top) > 0)
////                            layoutParams.height += y - rect.top;
////                        mImageView.setLayoutParams(layoutParams);
////                        break;
////
////                    case "lefttop":
////                        sizeFlag = true;
////                        if(layoutParams.width > 200 || -(x - rect.left) > 0)
////                            layoutParams.width += x - rect.left;
////                        if(layoutParams.height > 200 || -(y - rect.top) > 0)
////                            layoutParams.height += y - rect.top;
////                        mImageView.setLayoutParams(layoutParams);
////                        break;
////
////                    case "leftbottom":
////                        sizeFlag = true;
////                        if(layoutParams.width > 200 || (rect.left - x) > 0)
////                            layoutParams.width += x - rect.left;
////                        if(layoutParams.height > 200 || -(rect.bottom - y) > 0)
////                            layoutParams.height += y - rect.bottom;
////                        mImageView.setLayoutParams(layoutParams);
////                        break;
//
//                    case "bottom":
//                        sizeFlag = true;
//                        if(layoutParams.height > 200 || -(rect.bottom - y) > 0)
//                            layoutParams.height += y - rect.bottom;
//                        mImageView.setLayoutParams(layoutParams);
//                        break;
//
//                    case "right":
//                        sizeFlag = true;
//                        if(layoutParams.width > 200 || -(rect.right - x) > 0)
//                            layoutParams.width += x - rect.right;
//                        mImageView.setLayoutParams(layoutParams);
//                        break;
//
////                    case "top":
////                        sizeFlag = true;
////                        if(layoutParams.height > 200 /*|| (rect.top - y) > 0*/) {
////                            layoutParams.topMargin += -(y - rect.top);
////                            layoutParams.height += (y - rect.top);
////                        }
////                        mImageView.setLayoutParams(layoutParams);
////                        break;
//
////                    case "left":
////                        sizeFlag = true;
////                        if(layoutParams.width > 200 || (rect.left - x) > 0)
////                            layoutParams.width += x - rect.left;
////                        mImageView.setLayoutParams(layoutParams);
////                        break;
//
//                    default :
//                        if (!sizeFlag) {
//                            layoutParams.leftMargin = x - mXDelta;
//                            layoutParams.topMargin = y - mYDelta;
//                            layoutParams.rightMargin = -250;
//                            layoutParams.bottomMargin = -250;
//                            mImageView.setLayoutParams(layoutParams);
//                        }
//                        break;
//                }
//
//
//
//
////                if(Math.abs(rect.bottom - y) < 100) {
////                    sizeFlag = true;
////                    if(layoutParams.height > 200 || (y-rect.bottom) > 0)
////                        layoutParams.height += y - rect.bottom;
////                    if(Math.abs(rect.right - x) < 100) {
////                        if(layoutParams.width > 200 || (x - rect.right) > 0)
////                            layoutParams.width += x - rect.right;
////                    }
////                    mImageView.setLayoutParams(layoutParams);
////                }
////                else {
////                    if(Math.abs(rect.right - x) < 100) {
////                        sizeFlag = true;
////                        if(layoutParams.width > 200 || (x - rect.right) > 0)
////                            layoutParams.width += x - rect.right;
////                        mImageView.setLayoutParams(layoutParams);
////                    }
////                    else if(!sizeFlag){//위치변경
////                        layoutParams.leftMargin = x - mXDelta;
////                        layoutParams.topMargin = y - mYDelta;
////                        layoutParams.rightMargin = -250;
////                        layoutParams.bottomMargin = -250;
////                        mImageView.setLayoutParams(layoutParams);
////                        break;
////                    }
////                }
//
//                break;
//        }
//  //      mRoot.invalidate();
//        return true;
//    }
}
