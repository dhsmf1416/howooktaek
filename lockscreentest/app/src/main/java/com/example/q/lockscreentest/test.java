package com.example.q.lockscreentest;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Base64;
import java.util.HashMap;

import static android.Manifest.permission.RECORD_AUDIO;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class test extends AppCompatActivity {

    MediaRecorder recorder=new MediaRecorder();
    Button recb;
    Button pauseb;
    Button post;
    MediaPlayer mPlayer = null;
    boolean isPlaying = false;
    boolean isPausing = false;
    Button mBtPlay = null;
    String path;
    String oldpath="init";
    final int P_RECORD_AUDIO=77;
    final int W_RECORD_AUDIO=78;
    MediaPlayer mediaPlayer = new MediaPlayer();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.task_recording);
        if ((ActivityCompat.checkSelfPermission(getApplicationContext(), RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED)
                ||(ActivityCompat.checkSelfPermission(getApplicationContext(), WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)  ){

            ActivityCompat.requestPermissions(this, new String[]{RECORD_AUDIO,WRITE_EXTERNAL_STORAGE},
                    P_RECORD_AUDIO);
        }

        String h="[B@43857dd";
        mp3File(h.getBytes());
        post=findViewById(R.id.post);
        recb =findViewById(R.id.recb);
        pauseb =findViewById(R.id.pauseb);
        mBtPlay=findViewById(R.id.stream);

        recb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isRecording==false){
                    startRec();
                    //recorder.start();
                    isRecording=true;
                    recb.setText("녹음그만");
                }
                else{
                    stopRec();
                    isRecording=false;
                    recb.setText("녹음시작");}
            }
        });

        pauseb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    if(isPausing==false){
                        recorder.pause();
                        recb.setVisibility(View.INVISIBLE);
                        pauseb.setText("다시 녹음시작");
                        isPausing=true;
                    }
                    else{
                        recorder.resume();
                        recb.setVisibility(View.VISIBLE);
                        pauseb.setText("일시정지하기");
                        isPausing=false;

                    }
                }
            }
        });
        mBtPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isPlaying == false) {
                    try {
                        mPlayer.setDataSource(path);
                        mPlayer.prepare();
                    }catch (Exception e) {
                        e.printStackTrace();
                    }
                    mPlayer.start();

                    isPlaying = true;
                    mBtPlay.setText("듣기중지");
                }
                else {
                    mPlayer.reset();

                    isPlaying = false;
                    mBtPlay.setText("들어보기");
                }
            }
        });

        Intent intent=getIntent();
        final Double lan=intent.getDoubleExtra("lat",0);
        final Double lon=intent.getDoubleExtra("long",0);
        Toast.makeText(getApplicationContext(), lan+","+lon, Toast.LENGTH_SHORT).show();

        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // String k="";
                /*
                FileInputStream f=null;
                DataInputStream in=null;
                try{
                    f=new FileInputStream(path);
                    in=new DataInputStream(f);
                    int j=0;
                    while(in.read()!=-1){j++;}
                    byte [] b=new byte[j];
                    int i=0;
                    while(in.read()!=-1){
                        b[i]=in.readByte();
                    }
                    k=b.toString();
                    String actualArray = Arrays.toString(b);
                    Log.d("Str",actualArray);
                }catch(IOException e){

                }*/
                //File ff=new File(path);

                /*
                Path mpath = null;
                String k="";
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                    mpath = Paths.get(path);
                }
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                    try {
                        byte[] data = Files.readAllBytes(mpath);
                        byte[] encoded = Base64.getEncoder().encode(data);

                        k=data.toString();
                        Log.d("Str",k);
                        String h="[B@43857dd";
                        mp3File(h.getBytes());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }*/
                Uri i= Uri.parse(path);

                File sourceFile = new File(i.getPath());
                try {
                    FileInputStream fileInputStream = new FileInputStream(sourceFile);
                    int bytesAvailable = 0;

                    bytesAvailable = fileInputStream.available();

                    int maxBufferSize=1*1024*1024;
                    int bufferSize = Math.min(bytesAvailable, maxBufferSize);
                    byte[] buffer;
                    buffer = new byte[bufferSize];
                    int bytesRead;

                    bytesRead = fileInputStream.read(buffer, 0, bufferSize);


                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }catch (IOException e) {
                    e.printStackTrace();
                }
                // Log.d("StrRE",k);

            }
        });
        mPlayer = new MediaPlayer();
        mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

            @Override
            public void onCompletion(MediaPlayer mp) {
                mPlayer.reset();
                isPlaying = false;
                mBtPlay.setText("들어보기");
            }
        });

    }
    private void mp3File(byte[] bytearray) {
        try {
            File tempFile = File.createTempFile("mobile", "mp3", getCacheDir());
            tempFile.deleteOnExit();
            FileOutputStream fos = new FileOutputStream(tempFile);
            fos.write(bytearray);
            fos.close();
            mediaPlayer.reset();

            FileInputStream fis = new FileInputStream(tempFile);
            mediaPlayer.setDataSource(fis.getFD());

            mediaPlayer.prepare();
            mediaPlayer.start();
        } catch (IOException ex) {
            String s = ex.toString();
            ex.printStackTrace();
        }
    }
    public void startRec(){

        try {

            File file= Environment.getExternalStorageDirectory();
            //갤럭시 S4기준으로 /storage/emulated/0/의 경로를 갖고 시작한다.
            if(oldpath!="init"){
                Uri i= Uri.parse(oldpath);
                File f=new File(i.getPath());
                f.delete();
            }
            path=file.getAbsolutePath()+"/"+"recoder_"+System.currentTimeMillis()+".mp3";
            oldpath=path;
            recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            //첫번째로 어떤 것으로 녹음할것인가를 설정한다. 마이크로 녹음을 할것이기에 MIC로 설정한다.
            recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
            //이것은 파일타입을 설정한다. 녹음파일의경우 3gp로해야 용량도 작고 효율적인 녹음기를 개발할 수있다.
            recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
            //이것은 코덱을 설정하는 것이라고 생각하면된다.
            recorder.setOutputFile(path);
            //저장될 파일을 저장한뒤
            recorder.prepare();
            recorder.start();
            //시작하면된다.
            Toast.makeText(this, "start Record", Toast.LENGTH_LONG).show();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    boolean isRecording = false;
    public void stopRec(){
        try{
            Log.d("STOPREC","STOPPED");
            recorder.stop();
        }catch(RuntimeException e){
            e.printStackTrace();
        }
        //멈추는 것이다.
        Toast.makeText(this,"stop Record",Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if(oldpath!="init"){
            Uri i= Uri.parse(oldpath);
            File f=new File(i.getPath());
            f.delete();
        }
    }
}