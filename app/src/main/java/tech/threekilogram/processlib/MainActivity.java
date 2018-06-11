package tech.threekilogram.processlib;

import android.os.Bundle;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import tech.threekilogram.processlib.command.CommandServiceRemote;
import tech.threekilogram.processlib.remote.MainClientCore;
import tech.threekilogram.processlib.remote.MainServer;
import tech.threekilogram.service.inner.CommandManager;
import tech.threekilogram.service.remote.ServerConnection;

/**
 * @author wuxio
 */
public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mConnection = new ServerConnection();

    }

    //============================ 测试ServerService ============================

    private ServerConnection mConnection;
    private MainClientCore   mCore;


    public void bind(View view) {

        if (mCore == null) {
            mCore = new MainClientCore();
        }
        mConnection.connectToServer(this, mCore, MainServer.class);
    }


    public void unBind(View view) {

        mConnection.disConnectToServer(this);
    }


    public void sendToServer(View view) {

        try {
            mCore.sendMessageToServer(12);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    //============================ 测试CommandService ============================


    public void commandNormal(View view) {

        CommandManager.sendCommand(this, CommandServiceRemote.class, new Runnable() {


            @Override
            public void run() {

                String TAG = " normal command ";
                Log.i(TAG, "run:" + Thread.currentThread());
            }
        });
    }


    public void commandCross(View view) {

        CommandManager.sendCrossCommand(this, CommandServiceRemote.class, new Runnable() {


            @Override
            public void run() {

                String TAG = " cross command ";
                Log.i(TAG, "run:" + Thread.currentThread());
            }
        });
    }
}
