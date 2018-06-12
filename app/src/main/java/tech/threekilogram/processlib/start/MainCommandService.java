package tech.threekilogram.processlib.start;

import android.os.Bundle;
import android.util.Log;

import tech.threekilogram.service.command.BaseCommandService;
import tech.threekilogram.service.command.CommandReceiver;

/**
 * @author wuxio 2018-06-12:8:35
 */
public class MainCommandService extends BaseCommandService {

    private static final String TAG = "MainCommandService";


    @Override
    protected CommandReceiver createCommandReceiver() {

        return new Receiver();
    }


    private class Receiver implements CommandReceiver {

        @Override
        public void onCommandReceive(int what) {

            Log.i(TAG, "onCommandReceive:" + what);
        }


        @Override
        public void onCommandReceive(int what, Bundle bundle) {

            Log.i(TAG, "onCommandReceive:" + what + " " + bundle);
        }
    }
}
