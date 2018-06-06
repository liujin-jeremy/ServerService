package tech.threekilogram.service;

import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;

/**
 * 用于和客户端通信
 *
 * @author wuxio 2018-06-02:21:35
 */
class ServerHandler extends Handler {

    private static final String TAG              = "ServerHandler";
    static final         int    WHAT_BIND_CLIENT = 0xACCD_ffff;

    /**
     * server messenger ,used for communication
     */
    private Messenger mServerMessenger;
    private Messenger mClientMessenger;

    /**
     * 将消息转发给它处理
     */
    private BaseServerCore mServerCore;


    ServerHandler(BaseServerCore serverCore) {

        mServerMessenger = new Messenger(this);
        mServerCore = serverCore;
    }


    /**
     * 用于{@link BaseServerService#onBind(Intent)} 的返回值
     *
     * @return binder
     */
    IBinder getBinder() {

        return mServerMessenger.getBinder();
    }


    @Override
    public void handleMessage(Message msg) {

        switch (msg.what) {

            case WHAT_BIND_CLIENT:
                bindClient(msg);
                break;
            default:
                mServerCore.handleMessage(msg);
                break;
        }
    }


    /**
     * get message.replyTo as clientMessenger
     */
    private void bindClient(Message msg) {

        mClientMessenger = msg.replyTo;
    }


    /**
     * 向远程服务发行消息
     *
     * @param msg 消息
     * @throws RemoteException 如果服务不再了会触发异常
     */
    void sendMessageToClient(Message msg) throws RemoteException {

        mClientMessenger.send(msg);
    }

}
