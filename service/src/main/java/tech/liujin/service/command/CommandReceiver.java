package tech.liujin.service.command;

import android.os.Bundle;

/**
 * @author wuxio 2018-06-12:7:30
 */
public interface CommandReceiver {

      /**
       * 收到command
       *
       * @param what 标识
       */
      void onCommandReceive ( int what );

      /**
       * 收到带附件的command
       *
       * @param what 标识
       * @param bundle 附件包
       */
      void onCommandReceive ( int what, Bundle bundle );
}
