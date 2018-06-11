package tech.threekilogram.service.remote;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;

/**
 * 管理和远程服务的连接
 *
 * @author wuxio 2018-06-02:21:36
 */
public class ServerConnection implements ServiceConnection {

    /**
     * 远端服务返回的binder,用于检查远端状态
     */
    private IBinder        mBinder;
    /**
     * 用于和远端服务交互
     */
    private BaseClientCore mClientCore;

    /**
     * 标记当前是否已经处于绑定状态
     */
    private boolean isConnected;


    /**
     * 绑定远程连接
     *
     * @param context    context
     * @param clientCore 客户端核心逻辑封装
     */
    public < T extends BaseServerService > void connectToServer(
            Context context,
            BaseClientCore clientCore,
            Class< T > serviceClazz) {

        if (!isConnected) {

            mClientCore = clientCore;
            Intent intent = new Intent(context, serviceClazz);
            context.bindService(intent, this, Context.BIND_AUTO_CREATE);
        }
    }


    public void disConnectToServer(Context context) {

        if (isConnected) {
            context.unbindService(this);
            mClientCore.onFinish();
            isConnected = false;
        }
    }


    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {

        /* 只有连接成功之后才设置状态为已经连接 */

        isConnected = true;
        mBinder = service;

        /* 设置 mClientCore 变量 */

        mClientCore.init(service);

        /* 通知 mClientCore 已将连接 */

        mClientCore.onServiceConnected(name);
    }


    @Override
    public void onServiceDisconnected(ComponentName name) {

        isConnected = false;

        /* 通知 mClientCore 连接中断 */

        mClientCore.onServiceDisconnected(name);
    }


    @Override
    public void onBindingDied(ComponentName name) {

        isConnected = false;

        /* 通知 mClientCore 连接中断 */

        mClientCore.onBindingDied(name);
    }


    /**
     * 查看 binder 对应的进程是否存活
     * Check to see if the object still exists.
     *
     * @return Returns false if the
     * hosting process is gone, otherwise the result (always by default
     * true)
     */
    public final boolean ping() {

        return mBinder.pingBinder();
    }
}
