package tech.threekilogram.processlib;

import android.app.Service;
import android.os.Bundle;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import tech.threekilogram.processlib.command.CommandService;
import tech.threekilogram.processlib.remote.MainClientCore;
import tech.threekilogram.processlib.remote.MainServer;
import tech.threekilogram.processlib.start.MainCommandService;
import tech.threekilogram.service.command.CommandServiceManager;
import tech.threekilogram.service.command.ServiceCommand;
import tech.threekilogram.service.remote.ServerConnection;

/**
 * @author wuxio
 */
public class MainActivity extends AppCompatActivity {

      private ServerConnection mConnection;

      //============================ 测试ServerService ============================
      private MainClientCore mCore;

      @Override
      protected void onCreate (Bundle savedInstanceState) {

            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            mConnection = new ServerConnection();
      }

      public void bind (View view) {

            if(mCore == null) {
                  mCore = new MainClientCore();
            }
            mConnection.connectToServer(this, mCore, MainServer.class);
      }

      public void unBind (View view) {

            mConnection.disConnectToServer(this);
      }

      public void sendToServer (View view) {

            try {
                  mCore.sendMessageToServer(12);
            } catch(RemoteException e) {
                  e.printStackTrace();
            }
      }

      //============================ 测试CommandService ============================

      public void commandNormal (View view) {

            CommandServiceManager.sendCommand(this, CommandService.class, new ServiceCommand() {

                  @Override
                  public void run (Service service) {

                        String TAG = " normal command ";
                        Log.i(TAG, "run:" + Thread.currentThread());
                  }
            });
      }

      public void commandStart (View view) {

            CommandServiceManager.sendCommand(this, MainCommandService.class, 12);
      }

      public void commandStart01 (View view) {

            Bundle bundle = new Bundle();
            bundle.putString("temp", "Hello");
            CommandServiceManager.sendCommand(this, MainCommandService.class, 12, bundle);
      }
}
