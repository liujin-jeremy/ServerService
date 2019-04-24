package tech.liujin.service.command;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.CallSuper;
import android.support.annotation.Nullable;
import java.lang.ref.WeakReference;
import java.util.ArrayList;

/**
 * 用于本地service 执行任务,不能执行跨进程任务
 *
 * @author wuxio 2018-06-11:16:51
 */
public class BaseInnerCommandService extends Service {

      private CommandHandler mCommandHandler;

      @Override
      public void onCreate ( ) {

            super.onCreate();
            mCommandHandler = new CommandHandler( this );
      }

      @CallSuper
      @Override
      public int onStartCommand ( Intent intent, int flags, int startId ) {

            if( intent.hasExtra( CommandServiceManager.KEY_COMMAND_INDEX ) ) {

                  int index = intent.getIntExtra( CommandServiceManager.KEY_COMMAND_INDEX, 0 );

                  if( index >= CommandServiceManager.START_INDEX ) {

                        ServiceCommand runnable = CommandServiceManager.getRunnable( index );
                        mCommandHandler.newCommandArrive( runnable );
                  }
            }
            return super.onStartCommand( intent, flags, startId );
      }

      @Override
      public void onDestroy ( ) {

            super.onDestroy();

            if( mCommandHandler != null ) {

                  mCommandHandler.removeCallbacksAndMessages( null );
            }
      }

      /**
       * don,t  use this
       */
      @Nullable
      @Override
      public IBinder onBind ( Intent intent ) {

            return null;
      }

      /**
       * 执行任务,使用 Handler 执行任务,防止阻塞{@link #onStartCommand(Intent, int, int)}
       */
      private static class CommandHandler extends Handler {

            final static int WHAT_NEW_COMMAND = 0b100010101;

            private ArrayList<ServiceCommand>              mCommands = new ArrayList<>();
            private WeakReference<BaseInnerCommandService> mReference;

            public CommandHandler ( BaseInnerCommandService service ) {

                  mReference = new WeakReference<>( service );
            }

            void newCommandArrive ( ServiceCommand command ) {

                  mCommands.add( command );
                  sendEmptyMessage( WHAT_NEW_COMMAND );
            }

            @Override
            public void handleMessage ( Message msg ) {

                  int what = msg.what;

                  if( what == WHAT_NEW_COMMAND ) {

                        while( mCommands.size() > 0 ) {
                              ServiceCommand command = mCommands.remove( 0 );
                              command.run( mReference.get() );
                        }
                  }
            }
      }
}
