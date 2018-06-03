package tech.threekilogram.processlib;

import android.os.Bundle;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import tech.threekilogram.service.ServerConnection;

/**
 * @author wuxio
 */
public class MainActivity extends AppCompatActivity {


    private ServerConnection mConnection;
    private MainClientCore   mCore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mConnection = new ServerConnection();

    }


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

}
