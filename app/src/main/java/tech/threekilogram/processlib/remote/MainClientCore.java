package tech.threekilogram.processlib.remote;

import android.content.ComponentName;
import android.os.Message;
import android.util.Log;
import tech.liujin.service.remote.BaseClientCore;
import tech.liujin.service.remote.ServerConnection;

/**
 * @author wuxio 2018-06-03:13:43
 */
public class MainClientCore extends BaseClientCore {

      private static final String TAG = "MainClientCore";

      @Override
      protected void onStart ( ComponentName name ) {

            /* 和服务端已经连接了 */

            Log.i( TAG, "onStart:" + name.toShortString() );
      }

      @Override
      protected void onServiceDisconnected ( ComponentName name ) {

            /* 意外断开连接了 */

            Log.i( TAG, "onServiceDisconnected:" + name.toShortString() );
      }

      @Override
      protected void onFinish ( ServerConnection connection ) {

            /* 主动和服务端断开连接了 */

            Log.i( TAG, "onFinish:" + "" );
      }

      @Override
      protected void handleMessage ( Message msg ) {

            /* 此处可以接收服务端消息,根据服务端消息处理不同事件 */

            Log.i( TAG, "handleMessage:" + msg.what );
      }
}
