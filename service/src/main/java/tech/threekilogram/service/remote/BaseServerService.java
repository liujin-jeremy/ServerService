package tech.threekilogram.service.remote;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

/**
 * @author wuxio
 */
public abstract class BaseServerService extends Service {

    private static final String TAG = "BaseServerService";

    private BaseServerCore mServerCore;


    public BaseServerService() {

        mServerCore = makeServerCore();
    }


    /**
     * 创建一个核心逻辑类
     *
     * @return {@link BaseServerCore}
     */
    protected abstract BaseServerCore makeServerCore();


    @Override
    public void onCreate() {

        mServerCore.onCreate(this);
    }


    @Override
    public IBinder onBind(Intent intent) {

        mServerCore.onStart(intent);
        return mServerCore.getBinder();
    }


    @Override
    public boolean onUnbind(Intent intent) {

        mServerCore.onStop(intent);
        return super.onUnbind(intent);
    }


    @Override
    public void onDestroy() {

        mServerCore.onDestroy();
    }
}
