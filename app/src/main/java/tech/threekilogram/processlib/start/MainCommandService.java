package tech.threekilogram.processlib.start;

import android.os.Bundle;
import android.util.Log;
import tech.liujin.service.command.BaseCommandService;
import tech.liujin.service.command.CommandReceiver;

/**
 * @author wuxio 2018-06-12:8:35
 */
public class MainCommandService extends BaseCommandService {

      private static final String TAG = "MainCommandService";

      /**
       * 实现该方法,绑定消息处理类到service
       *
       * @return 消息接收类
       */
      @Override
      protected CommandReceiver createCommandReceiver ( ) {

            return new Receiver();
      }

      /**
       * 该类接收收到的消息,根据消息进行逻辑处理
       */
      private class Receiver implements CommandReceiver {

            @Override
            public void onCommandReceive ( int what ) {

                  Log.i( TAG, "onCommandReceive:" + what );
            }

            @Override
            public void onCommandReceive ( int what, Bundle bundle ) {

                  Log.i( TAG, "onCommandReceive:" + what + " " + bundle );
            }
      }
}
