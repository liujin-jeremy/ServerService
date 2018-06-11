package tech.threekilogram.service.remote;

import android.content.ComponentName;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;

/**
 * @author wuxio 2018-06-03:8:51
 */
public abstract class BaseClientCore {


    /**
     * 用于和远端服务通信,基于Messenger
     */
    private ClientHandler mClientHandler;


    /**
     * 初始化
     *
     * @param binder binder from {@link ServerConnection#onServiceConnected(ComponentName, IBinder)}
     */
    void init(IBinder binder) {

        mClientHandler = new ClientHandler(binder, this);
    }


    /**
     * 和service连接后回调
     * when connect to service this called
     *
     * @param name service componentName
     */
    protected abstract void onServiceConnected(ComponentName name);


    /**
     * 当处于绑定状态,远端服务意外断开连接之后回调
     * when Disconnected to service this called
     *
     * @param name service componentName
     */
    protected void onServiceDisconnected(ComponentName name) {

    }


    /**
     * Finish结束,处理完逻辑了,解绑服务之后回调
     */
    protected void onFinish() {

    }


    /**
     * called this when binding is died
     *
     * @param name service componentName
     */
    protected void onBindingDied(ComponentName name) {

    }


    /**
     * 获取客户端的handler,使用该handler发送的消息会在{@link #handleMessage(Message)}收到
     *
     * @return handler
     */
    public Handler getHandler() {

        return mClientHandler;
    }


    /**
     * 处理各种消息
     *
     * @param msg 消息,来自服务端或者自己的handler{@link #getHandler()}发送的消息
     */
    protected abstract void handleMessage(Message msg);


    /**
     * 向服务器发动一条消息
     *
     * @param what 消息表示
     * @throws RemoteException 如果远程服务不再了,会触发异常
     */
    public final void sendMessageToServer(int what) throws RemoteException {

        Message message = Message.obtain();
        message.what = what;

        mClientHandler.sendMessageToServer(message);
    }


    /**
     * 向服务器发动一条消息
     *
     * @param what   消息表示
     * @param bundle 携带额外的数据
     * @throws RemoteException 如果远程服务不再了,会触发异常
     */
    public final void sendMessageToServer(int what, Bundle bundle) throws RemoteException {

        Message message = Message.obtain();
        message.what = what;
        message.setData(bundle);

        mClientHandler.sendMessageToServer(message);
    }


    /**
     * 向服务器发动一条消息
     *
     * @param msg message 发送给远程服务端的
     * @throws RemoteException 如果远程服务不再了,会触发异常
     */
    public final void sendMessageToServer(Message msg) throws RemoteException {

        mClientHandler.sendMessageToServer(msg);
    }
}
