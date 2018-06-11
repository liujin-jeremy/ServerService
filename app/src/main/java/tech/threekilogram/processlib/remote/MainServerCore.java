package tech.threekilogram.processlib.remote;

import android.content.Context;
import android.content.Intent;
import android.os.Message;
import android.os.RemoteException;
import android.util.Log;

import tech.threekilogram.service.remote.BaseServerCore;

/**
 * @author wuxio 2018-06-03:13:46
 */
public class MainServerCore extends BaseServerCore {

    private static final String TAG = "MainServerCore";


    @Override
    protected void onCreate(Context context) {

        Log.i(TAG, "onCreate:" + "");
        super.onCreate(context);
    }


    @Override
    protected void onStart(Intent intent) {

        Log.i(TAG, "onStart:" + "");
    }


    @Override
    protected void onStop(Intent intent) {

        Log.i(TAG, "onStop:" + "");
        super.onStop(intent);
    }


    @Override
    protected void onDestroy() {

        Log.i(TAG, "onDestroy:" + "");
        super.onDestroy();
    }


    @Override
    protected void handleMessage(Message msg) {

        Log.i(TAG, "handleMessage:" + msg.what);

        try {
            sendMessageToClient(msg.what);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
