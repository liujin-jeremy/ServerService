package tech.threekilogram.processlib;

import android.content.ComponentName;
import android.os.Message;
import android.util.Log;

import tech.threekilogram.service.BaseClientCore;

/**
 * @author wuxio 2018-06-03:13:43
 */
public class MainClientCore extends BaseClientCore {

    private static final String TAG = "MainClientCore";


    @Override
    protected void onServiceConnected(ComponentName name) {

        Log.i(TAG, "onServiceConnected:" + name.toShortString());
    }


    @Override
    protected void onServiceDisconnected(ComponentName name) {

        Log.i(TAG, "onServiceDisconnected:" + name.toShortString());
    }


    @Override
    protected void onFinish() {

        Log.i(TAG, "onFinish:" + "");
    }


    @Override
    protected void handleMessage(Message msg) {

        Log.i(TAG, "handleMessage:" + msg.what);
    }
}
