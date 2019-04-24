package tech.liujin.service.remote;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

/**
 * 通过封装 messenger 用于service 跨进程通信
 *
 * @author wuxio
 */
@Deprecated
public abstract class BaseServerService extends Service {

      private BaseServerCore mServerCore;

      public BaseServerService ( ) {

            mServerCore = makeServerCore();
      }

      /**
       * 创建一个核心逻辑类
       *
       * @return {@link BaseServerCore}
       */
      protected abstract BaseServerCore makeServerCore ( );

      @Override
      public void onCreate ( ) {

            mServerCore.onCreate( this );
      }

      @Override
      public void onDestroy ( ) {

            mServerCore.onDestroy();
      }

      @Override
      public IBinder onBind ( Intent intent ) {

            mServerCore.onStart( intent );
            return mServerCore.getBinder();
      }

      @Override
      public boolean onUnbind ( Intent intent ) {

            mServerCore.onStop( intent );
            return super.onUnbind( intent );
      }
}
