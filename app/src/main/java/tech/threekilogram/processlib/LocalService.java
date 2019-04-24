package tech.threekilogram.processlib;

import android.util.Log;
import tech.liujin.service.LocalCommandService;

/**
 * @author wuxio 2018-06-11:17:45
 */
public class LocalService extends LocalCommandService {

      private static final String TAG = LocalService.class.getSimpleName();

      @Override
      protected void onCommandReceive ( int what ) {

            super.onCommandReceive( what );
            Log.i( TAG, "onCommandReceive: " + what );
      }

      @Override
      protected void onCommandReceive ( Object bundle ) {

            super.onCommandReceive( bundle );
            Log.i( TAG, "onCommandReceive: " + bundle );
      }

      @Override
      protected void onCommandReceive ( int what, Object bundle ) {

            super.onCommandReceive( what, bundle );
            Log.i( TAG, "onCommandReceive: " + what + " " + bundle );
      }
}
