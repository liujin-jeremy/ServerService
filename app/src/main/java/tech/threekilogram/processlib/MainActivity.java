package tech.threekilogram.processlib;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import tech.liujin.service.command.CommandManager;

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

      public void commandTest00 ( View view ) {

            CommandManager.sendRemoteCommand( this, RemoteService.class, 0 );
      }

      public void commandTest01 ( View view ) {

            Bundle bundle = new Bundle();
            bundle.putString( "temp", "Hello" );
            CommandManager.sendRemoteCommand( this, RemoteService.class, bundle );
      }

      public void commandTest02 ( View view ) {

            Bundle bundle = new Bundle();
            bundle.putString( "temp", "Hello" );
            CommandManager.sendRemoteCommand( this, RemoteService.class, 1, bundle );
      }

      public void commandTest03 ( View view ) {

            CommandManager.sendLocalCommand( this, LocalService.class, 2 );
      }

      public void commandTest04 ( View view ) {

            String s = "Hello World";
            CommandManager.sendLocalCommand( this, LocalService.class, s );
      }

      public void commandTest05 ( View view ) {

            CommandManager.sendLocalCommand( this, LocalService.class, 3, "Hello Local Service" );
      }
}
