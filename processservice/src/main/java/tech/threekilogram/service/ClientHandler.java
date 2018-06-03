package tech.threekilogram.service;

import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;

/**
 * @author wuxio 2018-06-02:21:37
 */
class ClientHandler extends Handler {

    private static final String TAG = "ClientHandler";

    private Messenger      mServerMessenger;
    private Messenger      mClientMessenger;
    private BaseClientCore mClientCore;


    ClientHandler(IBinder binder, BaseClientCore clientCore) {

        mServerMessenger = new Messenger(binder);
        mClientMessenger = new Messenger(this);

        mClientCore = clientCore;

        makeServerBindClient();
    }


    /**
     * 将客户端的Messenger发送给远程服务端,用于两方通信
     */
    private void makeServerBindClient() {

        Message message = Message.obtain();
        message.what = ServerHandler.WHAT_BIND_CLIENT;
        message.replyTo = mClientMessenger;

        try {
            mServerMessenger.send(message);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }


    /**
     * 向远程服务发行消息
     *
     * @param msg 消息
     * @throws RemoteException 如果服务不再了会触发异常
     */
    void sendMessageToServer(Message msg) throws RemoteException {

        mServerMessenger.send(msg);
    }


    /**
     * @param msg 收到的消息,全部转发给{@link BaseClientCore#handleMessage(Message)}处理
     */
    @Override
    public void handleMessage(Message msg) {

        mClientCore.handleMessage(msg);
    }
}
