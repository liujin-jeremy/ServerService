package tech.threekilogram.service.command;

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
public abstract class BaseCommandService extends Service {

      private CommandHandler mCommandHandler;

      public BaseCommandService ( ) {

            CommandReceiver receiver = createCommandReceiver();
            mCommandHandler = new CommandHandler( receiver );
      }

      /**
       * 创建命令接收器
       *
       * @return {@link CommandReceiver}
       */
      protected abstract CommandReceiver createCommandReceiver ( );

      @Override
      public int onStartCommand ( Intent intent, int flags, int startId ) {

            if( intent.hasExtra( CommandServiceManager.KEY_COMMAND_EXTRA ) ) {

                  Bundle bundle = intent.getBundleExtra( CommandServiceManager.KEY_COMMAND_EXTRA );
                  boolean which = bundle
                      .getBoolean( CommandServiceManager.KEY_COMMAND_WITHOUT_BUNDLE_EXTRA );

                  if( which ) {

                        mCommandHandler
                            .newCommandArrive(
                                bundle.getInt( CommandServiceManager.KEY_COMMAND_WHAT ) );
                  } else {

                        mCommandHandler
                            .newCommandArrive(
                                bundle.getInt( CommandServiceManager.KEY_COMMAND_WHAT ),
                                bundle
                            );
                  }
            }

            return super.onStartCommand( intent, flags, startId );
      }

      @Nullable
      @Override
      public IBinder onBind ( Intent intent ) {

            return null;
      }

      private static class CommandHandler extends Handler {

            private static final int WHAT_COMMAND       = 0b100101010;
            private static final int WHAT_COMMAND_EXTRA = 0b100101011;

            private CommandReceiver mCommandReceiver;

            public CommandHandler ( CommandReceiver commandReceiver ) {

                  mCommandReceiver = commandReceiver;
            }

            public void newCommandArrive ( int commandWhat ) {

                  Message message = Message.obtain();
                  message.what = WHAT_COMMAND;
                  message.arg1 = commandWhat;
                  sendMessage( message );
            }

            public void newCommandArrive ( int commandWhat, Bundle bundle ) {

                  Message message = Message.obtain();
                  message.what = WHAT_COMMAND_EXTRA;
                  message.arg1 = commandWhat;
                  message.setData( bundle );
                  sendMessage( message );
            }

            @Override
            public void handleMessage ( Message msg ) {

                  switch( msg.what ) {

                        case WHAT_COMMAND:
                              mCommandReceiver.onCommandReceive( msg.arg1 );
                              break;

                        case WHAT_COMMAND_EXTRA:
                              mCommandReceiver.onCommandReceive( msg.arg1, msg.getData() );
                              break;
                        default:
                              break;
                  }
            }
      }
}
