package tech.threekilogram.service.inner;

import android.content.Context;
import android.content.Intent;
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
    static final String KEY_COMMAND_INDEX = "tech.threekilogram.command.service:"
            + UUID.randomUUID().toString();

    static ArrayMap< Integer, Runnable > sRunnableArrayMap = new ArrayMap<>();
    static AtomicInteger                 sAtomicInteger    = new AtomicInteger(1010);


    static Runnable getRunnable(int index) {

        return sRunnableArrayMap.get(index);
    }


    /**
     * 发送任务给service执行
     */
    public static < T extends BaseCommandService > void sendCommand(
            Context context,
            Class< T > commandServiceClazz,
            Runnable runnable) {

        Intent intent = new Intent(context, commandServiceClazz);

        int index = sAtomicInteger.getAndAdd(1);
        sRunnableArrayMap.put(index, runnable);

        intent.putExtra(KEY_COMMAND_INDEX, index);
        context.startService(intent);
    }
}
