package tech.threekilogram.processlib;

import android.app.Service;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import tech.liujin.service.command.CommandServiceManager;
import tech.liujin.service.command.ServiceCommand;
import tech.threekilogram.processlib.command.CommandService;
import tech.threekilogram.processlib.start.MainCommandService;

/**
 * @author wuxio
 */
public class MainActivity extends AppCompatActivity {

      @Override
      protected void onCreate ( Bundle savedInstanceState ) {

            super.onCreate( savedInstanceState );
            setContentView( R.layout.activity_main );
      }

      //============================ 测试CommandService ============================

      public void commandNormal ( View view ) {

            CommandServiceManager.sendCommand( this, CommandService.class, new ServiceCommand() {

                  @Override
                  public void run ( Service service ) {

                        String TAG = " normal command ";
                        Log.i( TAG, "run:" + Thread.currentThread() );
                  }
            } );
      }

      public void commandStart ( View view ) {

            CommandServiceManager.sendCommand( this, MainCommandService.class, 12 );
      }

      public void commandStart01 ( View view ) {

            Bundle bundle = new Bundle();
            bundle.putString( "temp", "Hello" );
            CommandServiceManager.sendCommand( this, MainCommandService.class, 12, bundle );
      }
}
