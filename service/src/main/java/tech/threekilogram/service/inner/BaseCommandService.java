package tech.threekilogram.service.inner;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;

import java.util.ArrayList;

/**
 * 用于本地service 执行任务,不能执行跨进程任务
 *
 * @author wuxio 2018-06-11:16:51
 */
public class BaseCommandService extends Service {

    private CommandHandler mCommandHandler = new CommandHandler();


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        if (intent.hasExtra(CommandManager.KEY_COMMAND_INDEX)) {

            int index = intent.getIntExtra(CommandManager.KEY_COMMAND_INDEX, 0);

            if (index > 0) {

                Runnable runnable = CommandManager.getRunnable(index);
                mCommandHandler.newCommandArrive(runnable);
            }
        }
        return super.onStartCommand(intent, flags, startId);
    }


    /**
     * don,t  use this
     */
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {

        return null;
    }


    /**
     * 执行任务,使用 Handler 执行任务,防止阻塞{@link #onStartCommand(Intent, int, int)}
     */
    private static class CommandHandler extends Handler {

        private ArrayList< Runnable > mCommands = new ArrayList<>();

        final static int WHAT_NEW_COMMAND = 0b100010101;


        void newCommandArrive(Runnable command) {

            mCommands.add(command);
            sendEmptyMessage(WHAT_NEW_COMMAND);
        }


        @Override
        public void handleMessage(Message msg) {

            int what = msg.what;

            if (what == WHAT_NEW_COMMAND) {

                while (mCommands.size() > 0) {
                    Runnable command = mCommands.remove(0);
                    command.run();
                }
            }
        }
    }
}
