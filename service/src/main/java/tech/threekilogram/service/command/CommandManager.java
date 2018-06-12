package tech.threekilogram.service.command;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.util.ArrayMap;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author wuxio 2018-06-11:17:03
 */
public class CommandManager {

    /**
     * 添加一个 UUID 防止被攻击
     */
    static final String KEY_COMMAND_INDEX = "tech.threekilogram.command.inner.service:"
            + UUID.randomUUID().toString();

    static final String KEY_COMMAND = "tech.threekilogram.command.start.service:"
            + UUID.randomUUID().toString();

    static final String KEY_COMMAND_EXTRA = "tech.threekilogram.command.start.extra.service:"
            + UUID.randomUUID().toString();
    static final int    START_INDEX       = 1010;

    private static ArrayMap< Integer, Runnable > sRunnableArrayMap = new ArrayMap<>();
    private static AtomicInteger                 sAtomicInteger    = new AtomicInteger(START_INDEX);


    static Runnable getRunnable(int index) {

        return sRunnableArrayMap.get(index);
    }


    /**
     * 发送任务给service执行
     */
    public static < T extends BaseInnerCommandService > void sendCommand(
            Context context,
            Class< T > commandServiceClazz,
            Runnable runnable) {

        Intent intent = new Intent(context, commandServiceClazz);

        int index = sAtomicInteger.getAndAdd(1);
        sRunnableArrayMap.put(index, runnable);

        intent.putExtra(KEY_COMMAND_INDEX, index);
        context.startService(intent);
    }


    /**
     * 发送任务给service执行
     */
    public static < T extends BaseCommandService > void sendCommand(
            Context context,
            Class< T > commandServiceClazz,
            int what) {

        Intent intent = new Intent(context, commandServiceClazz);

        intent.putExtra(KEY_COMMAND, what);
        context.startService(intent);
    }


    /**
     * 发送任务给service执行
     */
    public static < T extends BaseCommandService > void sendCommand(
            Context context,
            Class< T > commandServiceClazz,
            int what,
            Bundle bundle) {

        Intent intent = new Intent(context, commandServiceClazz);

        intent.putExtra(KEY_COMMAND, what);
        intent.putExtra(KEY_COMMAND_EXTRA, bundle);
        context.startService(intent);
    }
}
