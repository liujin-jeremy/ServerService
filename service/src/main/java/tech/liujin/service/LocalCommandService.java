package tech.liujin.service;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.CallSuper;
import android.support.annotation.Nullable;

/**
 * 用于本地 service 执行任务,不能执行跨进程任务
 *
 * @author wuxio 2018-06-11:16:51
 */
public class LocalCommandService extends Service {

      /**
       * 辅助处理命令
       */
      private CommandHandler mCommandHandler;

      @Override
      public void onCreate ( ) {

            super.onCreate();
            mCommandHandler = new CommandHandler( this );
      }

      @CallSuper
      @Override
      public int onStartCommand ( Intent intent, int flags, int startId ) {

            int type = intent.getIntExtra( CommandManager.KEY_COMMAND_TYPE, CommandManager.COMMAND_TYPE_NOT_DEFINE );
            switch( type ) {
                  case CommandManager.COMMAND_TYPE_WHAT:
                        mCommandHandler.newCommandArrive( intent.getIntExtra( CommandManager.KEY_COMMAND_WHAT, 0 ) );
                        break;
                  case CommandManager.COMMAND_TYPE_LOCAL_RUNNABLE:
                        Runnable runnable = (Runnable) CommandManager.remove(
                            intent.getIntExtra( CommandManager.KEY_COMMAND_LOCAL_EXTRA_INDEX, 0 )
                        );
                        if( runnable != null ) {
                              mCommandHandler.newCommandArrive( runnable );
                        }
                        break;
                  case CommandManager.COMMAND_TYPE_LOCAL_OBJECT:
                        mCommandHandler.newCommandArrive(
                            CommandManager.remove( intent.getIntExtra( CommandManager.KEY_COMMAND_LOCAL_EXTRA_INDEX, 0 ) )
                        );
                        break;
                  case CommandManager.COMMAND_TYPE_LOCAL_WHAT_OBJECT:
                        mCommandHandler.newCommandArrive(
                            intent.getIntExtra( CommandManager.KEY_COMMAND_WHAT, 0 ),
                            CommandManager.remove( intent.getIntExtra( CommandManager.KEY_COMMAND_LOCAL_EXTRA_INDEX, 0 ) )
                        );
                        break;
                  default:
                        break;
            }

            return super.onStartCommand( intent, flags, startId );
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
      protected void onCommandReceive ( Object bundle ) { }

      /**
       * 收到带附件的command
       *
       * @param what 标识
       * @param bundle 附件包
       */
      protected void onCommandReceive ( int what, Object bundle ) {}

      @Override
      public void onDestroy ( ) {

            super.onDestroy();

            if( mCommandHandler != null ) {
                  mCommandHandler.removeCallbacksAndMessages( null );
                  mCommandHandler.mCommandService = null;
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

            final static int RUNNABLE_COMMAND    = 0b100000000;
            final static int WHAT_COMMAND        = 0b100000001;
            final static int OBJECT_COMMAND      = 0b100000011;
            final static int WHAT_OBJECT_COMMAND = 0b100000111;

            private LocalCommandService mCommandService;

            private CommandHandler ( LocalCommandService commandService ) {

                  mCommandService = commandService;
            }

            void newCommandArrive ( int what ) {

                  Message obtain = Message.obtain();
                  obtain.what = WHAT_COMMAND;
                  obtain.arg1 = what;
                  sendMessage( obtain );
            }

            void newCommandArrive ( Runnable command ) {

                  Message obtain = Message.obtain();
                  obtain.what = RUNNABLE_COMMAND;
                  obtain.obj = command;
                  sendMessage( obtain );
            }

            void newCommandArrive ( Object o ) {

                  Message obtain = Message.obtain();
                  obtain.what = OBJECT_COMMAND;
                  obtain.obj = o;
                  sendMessage( obtain );
            }

            void newCommandArrive ( int what, Object o ) {

                  Message obtain = Message.obtain();
                  obtain.what = WHAT_OBJECT_COMMAND;
                  obtain.arg1 = what;
                  obtain.obj = o;
                  sendMessage( obtain );
            }

            @Override
            public void handleMessage ( Message msg ) {

                  try {
                        switch( msg.what ) {
                              case WHAT_COMMAND:
                                    mCommandService.onCommandReceive( msg.arg1 );
                                    break;
                              case OBJECT_COMMAND:
                                    mCommandService.onCommandReceive( msg.obj );
                                    break;
                              case WHAT_OBJECT_COMMAND:
                                    mCommandService.onCommandReceive( msg.arg1, msg.obj );
                                    break;
                              case RUNNABLE_COMMAND:
                                    Runnable run = (Runnable) msg.obj;
                                    run.run();
                                    break;
                        }
                  } catch(Exception e) {
                        e.printStackTrace();
                  }
            }
      }
}
