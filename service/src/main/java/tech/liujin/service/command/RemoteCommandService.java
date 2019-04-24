package tech.liujin.service.command;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;

/**
 * 用于service跨进程通信,主要通过 {@link #onStartCommand(Intent, int, int)}实现
 *
 * @author wuxio 2018-06-12:7:29
 */
public class RemoteCommandService extends Service {

      /**
       * 辅助处理命令
       */
      private CommandHandler mCommandHandler;

      public RemoteCommandService ( ) {

            mCommandHandler = new CommandHandler( this );
      }

      /**
       * 收到command
       *
       * @param what 标识
       */
      protected void onCommandReceive ( int what ) { }

      /**
       * 收到command
       *
       * @param bundle bundle
       */
      protected void onCommandReceive ( Bundle bundle ) { }

      /**
       * 收到带附件的command
       *
       * @param what 标识
       * @param bundle 附件包
       */
      protected void onCommandReceive ( int what, Bundle bundle ) {}

      @Override
      public int onStartCommand ( Intent intent, int flags, int startId ) {

            int type = intent.getIntExtra( CommandManager.KEY_COMMAND_TYPE, CommandManager.COMMAND_TYPE_NOT_DEFINE );

            switch( type ) {
                  case CommandManager.COMMAND_TYPE_WHAT:
                        mCommandHandler.newCommandArrive( intent.getIntExtra( CommandManager.KEY_COMMAND_WHAT, 0 ) );
                        break;
                  case CommandManager.COMMAND_TYPE_BUNDLE:
                        mCommandHandler.newCommandArrive( intent.getBundleExtra( CommandManager.KEY_COMMAND_BUNDLE ) );
                        break;
                  case CommandManager.COMMAND_TYPE_WHAT_BUNDLE:
                        mCommandHandler.newCommandArrive(
                            intent.getIntExtra( CommandManager.KEY_COMMAND_WHAT, 0 ),
                            intent.getBundleExtra( CommandManager.KEY_COMMAND_BUNDLE )
                        );
                        break;
                  default:
                        break;
            }

            return super.onStartCommand( intent, flags, startId );
      }

      @Nullable
      @Override
      public IBinder onBind ( Intent intent ) {

            return null;
      }

      /**
       * 辅助处理命令
       */
      private static class CommandHandler extends Handler {

            private static final int WHAT_COMMAND        = 0b100100000;
            private static final int BUNDLE_COMMAND      = 0b100100001;
            private static final int WHAT_BUNDLE_COMMAND = 0b100100011;

            private RemoteCommandService mService;

            private CommandHandler ( RemoteCommandService service ) {

                  mService = service;
            }

            private void newCommandArrive ( int commandWhat ) {

                  Message message = Message.obtain();
                  message.what = WHAT_COMMAND;
                  message.arg1 = commandWhat;
                  sendMessage( message );
            }

            private void newCommandArrive ( Bundle bundle ) {

                  Message message = Message.obtain();
                  message.what = BUNDLE_COMMAND;
                  message.setData( bundle );
                  sendMessage( message );
            }

            private void newCommandArrive ( int commandWhat, Bundle bundle ) {

                  Message message = Message.obtain();
                  message.what = WHAT_BUNDLE_COMMAND;
                  message.arg1 = commandWhat;
                  message.setData( bundle );
                  sendMessage( message );
            }

            @Override
            public void handleMessage ( Message msg ) {

                  switch( msg.what ) {

                        case WHAT_COMMAND:
                              mService.onCommandReceive( msg.arg1 );
                              break;
                        case BUNDLE_COMMAND:
                              mService.onCommandReceive( msg.getData() );
                              break;
                        case WHAT_BUNDLE_COMMAND:
                              mService.onCommandReceive( msg.arg1, msg.getData() );
                        default:
                              break;
                  }
            }
      }
}
