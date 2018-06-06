package tech.threekilogram.service;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;

/**
 * @author wuxio 2018-06-03:3:30
 */
public abstract class BaseServerCore {

    /**
     * 用于通信
     */
    private ServerHandler mServerHandler;


    public BaseServerCore() {

        mServerHandler = new ServerHandler(this);
    }


    /**
     * 当{@link BaseServerService#onCreate()}调用时回调
     *
     * @param context service context
     */
    protected void onCreate(Context context) {

    }


    /**
     * 当{@link BaseServerService#onBind(Intent)}调用时回调
     *
     * @param intent from {@link BaseServerService#onBind(Intent)}
     */
    protected abstract void onStart(Intent intent);


    /**
     * 当{@link BaseServerService#onUnbind(Intent)}调用时回调
     *
     * @param intent from {@link BaseServerService#onUnbind(Intent)}
     */
    protected void onStop(Intent intent) {

    }


    /**
     * 当{@link BaseServerService#onDestroy()}调用时回调
     */
    protected void onDestroy() {

    }


    /**
     * 用于{@link BaseServerService#onBind(Intent)} 的返回值
     *
     * @return binder
     */
    IBinder getBinder() {

        return mServerHandler.getBinder();
    }


    public Handler getHandler() {

        return mServerHandler;
    }


    /**
     * 处理各种消息
     *
     * @param msg 消息
     */
    protected abstract void handleMessage(Message msg);


    /**
     * 向客户端发动一条消息
     *
     * @param what 消息表示
     * @throws RemoteException 如果远程服务不再了,会触发异常
     */
    public final void sendMessageToClient(int what) throws RemoteException {

        Message message = Message.obtain();
        message.what = what;

        mServerHandler.sendMessageToClient(message);
    }


    /**
     * 向客户端发动一条消息
     *
     * @param what   消息表示
     * @param bundle 携带额外的数据
     * @throws RemoteException 如果远程服务不再了,会触发异常
     */
    public final void sendMessageToClient(int what, Bundle bundle) throws RemoteException {

        Message message = Message.obtain();
        message.what = what;
        message.setData(bundle);

        mServerHandler.sendMessageToClient(message);
    }


    /**
     * 向客户端发动一条消息
     *
     * @param msg message 发送给远程服务端的
     * @throws RemoteException 如果远程服务不再了,会触发异常
     */
    public final void sendMessageToClient(Message msg) throws RemoteException {

        mServerHandler.sendMessageToClient(msg);
    }
}
