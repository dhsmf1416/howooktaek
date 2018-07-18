package com.example.q.lockscreentest;

import android.app.KeyguardManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;

import java.util.ArrayList;

// ScreenReceiver : 화면이 꺼졌을때 ACTION_SCREEN_OFF intent를 받을 녀석

public class ScreenReceiver extends BroadcastReceiver {

    private KeyguardManager km = null;
    private KeyguardManager.KeyguardLock keyLock = null;
    private TelephonyManager telephonyManager = null;
    private boolean isPhoneIdle = true;

    @Override
    public void onReceive(Context context, Intent intent) {

        //ACTION_SCREEN_OFF intent를 받으면 이 액션을 실행한다.
        // 화면이 꺼질때마다 앱을 실행하는 것이겠군)
        if (intent.getAction().equals(Intent.ACTION_SCREEN_OFF)) {

            //km에다가 기본잠금화면을 넣어주는듯
            if (km == null)
                km = (KeyguardManager) context.getSystemService(Context.KEYGUARD_SERVICE);

            if (keyLock == null)
                keyLock = km.newKeyguardLock(Context.KEYGUARD_SERVICE);

            if(telephonyManager == null){
                telephonyManager = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
                telephonyManager.listen(phoneListener, PhoneStateListener.LISTEN_CALL_STATE);
            }

            if(isPhoneIdle) {
                //disableKeyguard(); //기본 잠금화면 없애기
                Intent i = new Intent(context, LockScreeniconActivity.class);
                //Activity에서 startActivity를 하는게 아니면 넣어줘야하는 flag
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);
            }
        }
    }


    public void reenableKeyguard() {
        keyLock.reenableKeyguard(); //기본 잠금화면 나타내기
    }

    public void disableKeyguard() {
        keyLock.disableKeyguard();
    }

    private PhoneStateListener phoneListener = new PhoneStateListener(){

        @Override
        public void onCallStateChanged(int state, String incomingNumber){
            switch(state){
                //정상상태
                case TelephonyManager.CALL_STATE_IDLE :
                    isPhoneIdle = true;
                    break;
                //벨이울리는중
                case TelephonyManager.CALL_STATE_RINGING :
                    isPhoneIdle = false;
                    break;
                //통화중
                case TelephonyManager.CALL_STATE_OFFHOOK :
                    isPhoneIdle = false;
                    break;

            }
        }
    };

}
