package tech.threekilogram.processlib;

import android.os.Bundle;
import android.util.Log;
import java.util.Set;
import tech.liujin.service.RemoteCommandService;

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
            Set<String> keySet = bundle.keySet();
            for( String s : keySet ) {
                  Object o = bundle.get( s );
                  Log.i( TAG, "onCommandReceive: " + o );
            }
      }

      @Override
      public void onCommandReceive ( int what, Bundle bundle ) {

            Set<String> keySet = bundle.keySet();
            for( String s : keySet ) {
                  Object o = bundle.get( s );
                  Log.i( TAG, "onCommandReceive: " + o + " " + what );
            }
      }
}
