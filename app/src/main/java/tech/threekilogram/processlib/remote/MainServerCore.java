package tech.threekilogram.processlib.remote;

import android.content.Context;
import android.content.Intent;
import android.os.Message;
import android.os.RemoteException;
import android.util.Log;
import tech.threekilogram.service.remote.BaseServerCore;

/**
 *
 *
 * @author wuxio 2018-06-03:13:46
 */
public class MainServerCore extends BaseServerCore {

      private static final String TAG = "MainServerCore";

      @Override
      protected void onCreate (Context context) {

            /* 服务端service 创建时回调,相当于service onCreate */

            Log.i(TAG, "onCreate:" + "");
            super.onCreate(context);
      }

      @Override
      protected void onStart (Intent intent) {

            /* 服务端service和客户端连接了,可以通信了,相当于 service onBind */

            Log.i(TAG, "onStart:" + "");
      }

      @Override
      protected void onStop (Intent intent) {

            /* 服务端service和客户端断开连接了,相当于 service unBind */

            Log.i(TAG, "onStop:" + "");
            super.onStop(intent);
      }

      @Override
      protected void onDestroy () {

            /* 服务端service 销毁时回调,相当于service onDestroy */

            Log.i(TAG, "onDestroy:" + "");
            super.onDestroy();
      }

      /**
       * @param msg 消息
       */
      @Override
      protected void handleMessage (Message msg) {

            /* 此处处理服务端发送来的消息 */

            Log.i(TAG, "handleMessage:" + msg.what);

            try {
                  //测试:接收到消息之后就发送回客户端
                  sendMessageToClient(msg.what);
            } catch(RemoteException e) {
                  e.printStackTrace();
            }
      }
}
