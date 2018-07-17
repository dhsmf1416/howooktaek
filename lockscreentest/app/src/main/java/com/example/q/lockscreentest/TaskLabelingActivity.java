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

public class TaskLabelingActivity extends AppCompatActivity implements View.OnTouchListener{

    ImageView mImageView;
    ViewGroup mRoot;
    private int mXDelta;
    private int mYDelta;
    boolean sizeFlag;
    Rect rect;

    Intent intent;
    byte[] img_binary;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        rect = new Rect();
        setContentView(R.layout.task_labeling);

        mRoot = (RelativeLayout) findViewById (R.id.labelingimage_layout);
        mImageView = (ImageView) findViewById(R.id.croprect);
        mImageView.setOnTouchListener(this);
        mImageView.bringToFront();


        TextView tv = findViewById(R.id.labelingtitle);
        tv.setText("마 찾아라");

        intent = getIntent();
        img_binary = intent.getByteArrayExtra("img_binary");


        ImageView iv = findViewById(R.id.labelingimage);
        iv.setImageBitmap(Post_LabelingTaskHttp.result_d);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {

        final int x = (int) event.getRawX();
        final int y = (int) event.getRawY();
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                sizeFlag = false;
                RelativeLayout.LayoutParams lParams = (RelativeLayout.LayoutParams) mImageView.getLayoutParams();
                mXDelta = x - lParams.leftMargin;
                mYDelta = y - lParams.topMargin;
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_POINTER_DOWN:
            case MotionEvent.ACTION_POINTER_UP:
                break;
            case MotionEvent.ACTION_MOVE:
                v.getGlobalVisibleRect(rect);

                RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) mImageView.getLayoutParams();
                String touchpoint = "";

                if(Math.abs(rect.bottom - y) < 80 && Math.abs(rect.right - x) < 80)
                    touchpoint = "rightbottom";
                else if(Math.abs(rect.right - x) < 80 && Math.abs(rect.top - y) < 80)
                    touchpoint = "righttop";
                else if(Math.abs(rect.top - y) < 80 && Math.abs(rect.left - x) < 80)
                    touchpoint = "lefttop";
                else if(Math.abs(rect.left - x) < 80 && Math.abs(rect.right - y) < 80)
                    touchpoint = "leftbottom";
                else if(Math.abs(rect.bottom - y) < 80 )
                    touchpoint = "bottom";
                else if(Math.abs(rect.right - x) < 80 )
                    touchpoint = "right";
                else if(Math.abs(rect.top - y) < 80 )
                    touchpoint = "top";
                else if(Math.abs(rect.left - x) < 80 )
                    touchpoint = "left";
                else
                    touchpoint = "";


//                System.out.println("오른쪽 마진 : " + layoutParams.rightMargin);
//                System.out.println("왼쪽 마진 : " + layoutParams.leftMargin);
//                System.out.println("위쪽 마진 : " + layoutParams.topMargin);
//                System.out.println("아래쪽 마진 : " + layoutParams.bottomMargin);
//                System.out.println(touchpoint);


                switch (touchpoint){

                    case "rightbottom":
                        sizeFlag = true;
                        if(layoutParams.width > 200 || (x - rect.right) > 0)
                            layoutParams.width += x - rect.right;
                        if(layoutParams.height > 200 || (y - rect.bottom) > 0)
                            layoutParams.height += y - rect.bottom;
                        mImageView.setLayoutParams(layoutParams);
                        break;
//
//                    case "righttop":
//                        sizeFlag = true;
//                        if(layoutParams.width > 200 || (x - rect.right) > 0)
//                            layoutParams.width += x - rect.right;
//                        if(layoutParams.height > 200 || -(y- rect.top) > 0)
//                            layoutParams.height += y - rect.top;
//                        mImageView.setLayoutParams(layoutParams);
//                        break;
//
//                    case "lefttop":
//                        sizeFlag = true;
//                        if(layoutParams.width > 200 || -(x - rect.left) > 0)
//                            layoutParams.width += x - rect.left;
//                        if(layoutParams.height > 200 || -(y - rect.top) > 0)
//                            layoutParams.height += y - rect.top;
//                        mImageView.setLayoutParams(layoutParams);
//                        break;
//
//                    case "leftbottom":
//                        sizeFlag = true;
//                        if(layoutParams.width > 200 || (rect.left - x) > 0)
//                            layoutParams.width += x - rect.left;
//                        if(layoutParams.height > 200 || -(rect.bottom - y) > 0)
//                            layoutParams.height += y - rect.bottom;
//                        mImageView.setLayoutParams(layoutParams);
//                        break;

                    case "bottom":
                        sizeFlag = true;
                        if(layoutParams.height > 200 || -(rect.bottom - y) > 0)
                            layoutParams.height += y - rect.bottom;
                        mImageView.setLayoutParams(layoutParams);
                        break;

                    case "right":
                        sizeFlag = true;
                        if(layoutParams.width > 200 || -(rect.right - x) > 0)
                            layoutParams.width += x - rect.right;
                        mImageView.setLayoutParams(layoutParams);
                        break;

//                    case "top":
//                        sizeFlag = true;
//                        if(layoutParams.height > 200 /*|| (rect.top - y) > 0*/) {
//                            layoutParams.topMargin += -(y - rect.top);
//                            layoutParams.height += (y - rect.top);
//                        }
//                        mImageView.setLayoutParams(layoutParams);
//                        break;

//                    case "left":
//                        sizeFlag = true;
//                        if(layoutParams.width > 200 || (rect.left - x) > 0)
//                            layoutParams.width += x - rect.left;
//                        mImageView.setLayoutParams(layoutParams);
//                        break;

                    default :
                        if (!sizeFlag) {
                            layoutParams.leftMargin = x - mXDelta;
                            layoutParams.topMargin = y - mYDelta;
                            layoutParams.rightMargin = -250;
                            layoutParams.bottomMargin = -250;
                            mImageView.setLayoutParams(layoutParams);
                        }
                        break;
                }




//                if(Math.abs(rect.bottom - y) < 100) {
//                    sizeFlag = true;
//                    if(layoutParams.height > 200 || (y-rect.bottom) > 0)
//                        layoutParams.height += y - rect.bottom;
//                    if(Math.abs(rect.right - x) < 100) {
//                        if(layoutParams.width > 200 || (x - rect.right) > 0)
//                            layoutParams.width += x - rect.right;
//                    }
//                    mImageView.setLayoutParams(layoutParams);
//                }
//                else {
//                    if(Math.abs(rect.right - x) < 100) {
//                        sizeFlag = true;
//                        if(layoutParams.width > 200 || (x - rect.right) > 0)
//                            layoutParams.width += x - rect.right;
//                        mImageView.setLayoutParams(layoutParams);
//                    }
//                    else if(!sizeFlag){//위치변경
//                        layoutParams.leftMargin = x - mXDelta;
//                        layoutParams.topMargin = y - mYDelta;
//                        layoutParams.rightMargin = -250;
//                        layoutParams.bottomMargin = -250;
//                        mImageView.setLayoutParams(layoutParams);
//                        break;
//                    }
//                }

                break;
        }
        mRoot.invalidate();
        return true;
    }
}
