package tech.liujin.service.remote;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;

/**
 * 该类用于服务端各种逻辑实现,该框架通过两端的messenger相互通信实现进程间通信,两端通过message的不同处理不同的事件
 * <p>
 * service 端逻辑类
 *
 * @author wuxio 2018-06-03:3:30
 */
@Deprecated
public abstract class BaseServerCore {

      /**
       * 辅助通信,提供服务端messenger,并且和客户端通信
       */
      private ServerHandler mServerHandler;

      public BaseServerCore ( ) {

            mServerHandler = new ServerHandler( this );
      }

      /**
       * 当{@link BaseServerService#onCreate()}调用时回调,用户在此处初始化
       *
       * @param context service context
       */
      protected void onCreate ( Context context ) {

      }

      /**
       * 当{@link BaseServerService#onBind(Intent)}调用时回调
       *
       * @param intent from {@link BaseServerService#onBind(Intent)}
       */
      protected abstract void onStart ( Intent intent );

      /**
       * 当{@link BaseServerService#onUnbind(Intent)}调用时回调
       *
       * @param intent from {@link BaseServerService#onUnbind(Intent)}
       */
      protected void onStop ( Intent intent ) {

      }

      /**
       * 当{@link BaseServerService#onDestroy()}调用时回调
       */
      protected void onDestroy ( ) {

      }

      /**
       * 用于{@link BaseServerService#onBind(Intent)} 的返回值
       *
       * @return binder
       */
      IBinder getBinder ( ) {

            return mServerHandler.getBinder();
      }

      /**
       * @return 服务端自己的handler, 用户可以使用该handler处理自己的事件
       */
      public Handler getHandler ( ) {

            return mServerHandler;
      }

      /**
       * 此处处理各种消息,消息可能来自客户端,可能来自服务端自己的handler自己{@link #getHandler()}
       *
       * @param msg 消息
       */
      protected abstract void handleMessage ( Message msg );

      /**
       * 向客户端发动一条消息
       *
       * @param what 消息标志
       *
       * @throws RemoteException 如果远程服务不再了,会触发异常
       */
      public final void sendMessageToClient ( int what ) throws RemoteException {

            Message message = Message.obtain();
            message.what = what;

            mServerHandler.sendMessageToClient( message );
      }

      /**
       * 向客户端发动一条消息
       *
       * @param what 消息标志
       * @param bundle 携带额外的数据
       *
       * @throws RemoteException 如果远程服务不再了,会触发异常
       */
      public final void sendMessageToClient ( int what, Bundle bundle ) throws RemoteException {

            Message message = Message.obtain();
            message.what = what;
            message.setData( bundle );

            mServerHandler.sendMessageToClient( message );
      }

      /**
       * 向客户端发动一条消息
       *
       * @param msg message 发送给远程服务端的
       *
       * @throws RemoteException 如果远程服务不再了,会触发异常
       */
      public final void sendMessageToClient ( Message msg ) throws RemoteException {

            mServerHandler.sendMessageToClient( msg );
      }
}
