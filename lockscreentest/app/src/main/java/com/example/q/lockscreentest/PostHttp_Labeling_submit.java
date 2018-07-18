package com.example.q.lockscreentest;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class PostHttp_Labeling_submit extends AsyncTask {

    Context parent = null;
    String img_binary = null;
    public static final String REQUEST_METHOD = "GET";
    public static final int READ_TIMEOUT = 15000;
    public static final int CONNECTION_TIMEOUT = 15000;
    ProgressDialog asyncDialog =null;

    public PostHttp_Labeling_submit(Context context){
        this.parent = context;
    }

    @Override
    protected void onPreExecute() {
        asyncDialog = new ProgressDialog(parent);
        asyncDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        asyncDialog.setMessage("최대한 빠르게 불러오고 있으니 ㄱㄷ");
        asyncDialog.show();
        super.onPreExecute();
    }


    @Override
    protected Object doInBackground(Object[] objects) {
        String url;
        InputStream is = null;
        String result = "";

        //inputstream  = 바이트 단위로 데이터를 읽는다. 외부로부터 읽어 들이는기능관련 클래스들
        //outputstream = 외부로 데이터를 전송합니다. 외부로 데이터를 전송하는 기능 관련 클래스들


        try {
            URL urlCon = new URL(objects[0].toString());
            HttpURLConnection httpCon = (HttpURLConnection) urlCon.openConnection();
            String json = ((JSONObject) objects[1]).toString();
            httpCon.setRequestProperty("Accept", "application/json");
            httpCon.setRequestProperty("Content-type", "application/json");

            //서버 response data를 json 형식의 타입으로 요청
            //httpCon.setRequestProperty("Accept", "application/json");

            // 타입설정(application/json) 형식으로 전송 (Request Body 전달시 application/json로 서버에 전달.)
            //httpCon.setRequestProperty("Content-type", "application/json");
            // OutputStream으로 POST 데이터를 넘겨주겠다는 옵션.
            httpCon.setDoOutput(true);
            // InputStream으로 서버로 부터 응답을 받겠다는 옵션.
            httpCon.setDoInput(true);

            OutputStream os = httpCon.getOutputStream();
//            os.write(json.getBytes("euc-kr"));
//            os.flush();
            os.write(json.getBytes("UTF-8"));

            // receive response as inputStream

            try {
                is = httpCon.getInputStream();
                // convert inputstream to string
                if (is != null)
                    result = convertInputStreamToString(is);
                else
                    result = "Something is wrong";

            } catch (IOException e) {

                e.printStackTrace();

            } finally {

                httpCon.disconnect();

            }

        } catch (IOException e) {

            e.printStackTrace();

        } catch (Exception e) {

            System.out.println("InputStream" + e.getLocalizedMessage());

        }
        img_binary = result;
        return result;

    }
    private String convertInputStreamToString(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        //while ((line = bufferedReader.readLine()) != null)

        line = bufferedReader.readLine();
        System.out.println("라인 넣는중 : "+ line);
        result += line;
        inputStream.close();
        return result;

    }

    @Override
    protected void onPostExecute(Object o) {
        TaskListItem Labeling_tli = new TaskListItem("labeling", "전형준 얼굴 인식 (7500P)");
        asyncDialog.dismiss();
        if (img_binary.length() > 10){
            LockScreenActivity.mTaskList.add(Labeling_tli);
        }
        LockScreenActivity.adapter.notifyDataSetChanged();
        super.onPostExecute(o);
    }

}