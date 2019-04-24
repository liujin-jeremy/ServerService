package tech.threekilogram.processlib;

import android.os.Bundle;
import android.util.Log;
import tech.liujin.service.command.RemoteCommandService;

/**
 * @author wuxio 2018-06-12:8:35
 */
public class RemoteService extends RemoteCommandService {

      private static final String TAG = "RemoteService";

      @Override
      public void onCommandReceive ( int what ) {

            Log.i( TAG, "onCommandReceive:" + what );
      }

      @Override
      protected void onCommandReceive ( Bundle bundle ) {

            super.onCommandReceive( bundle );
            Log.i( TAG, "onCommandReceive: " + bundle );
      }

      @Override
      public void onCommandReceive ( int what, Bundle bundle ) {

            Log.i( TAG, "onCommandReceive:" + what + " " + bundle );
      }
}
