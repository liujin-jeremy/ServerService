package tech.threekilogram.service.command;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.util.ArrayMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author wuxio 2018-06-11:17:03
 */
public class CommandServiceManager {

      static final String KEY_COMMAND_INDEX = "tech.threekilogram.command.inner.service:";
      static final String KEY_COMMAND_WHAT  = "tech.threekilogram.command.start.service:";
      static final String KEY_COMMAND_WHICH = "tech.threekilogram.command.start.service.which:";
      static final String KEY_COMMAND_EXTRA = "tech.threekilogram.command.start.extra.service:";
      static final int    START_INDEX       = 1010;
      private static ArrayMap<Integer, ServiceCommand> sRunnableArrayMap;
      private static AtomicInteger                     sAtomicInteger;

      // ========================= 本地service通信 =========================

      static ServiceCommand getRunnable (int index) {

            return sRunnableArrayMap == null ? null : sRunnableArrayMap.get(index);
      }

      /**
       * 发送任务给service执行
       */
      public static <T extends BaseInnerCommandService> void sendCommand (
          Context context,
          Class<T> commandServiceClazz,
          ServiceCommand runnable) {

            initFields();

            int index = sAtomicInteger.getAndAdd(1);
            sRunnableArrayMap.put(index, runnable);

            Intent intent = new Intent(context, commandServiceClazz);
            intent.putExtra(KEY_COMMAND_INDEX, index);
            context.startService(intent);
      }

      /**
       * 初始化变量
       */
      private static void initFields () {

            if(sRunnableArrayMap == null) {
                  sRunnableArrayMap = new ArrayMap<>();
            }
            if(sAtomicInteger == null) {
                  sAtomicInteger = new AtomicInteger(START_INDEX);
            }
      }

      // ========================= 跨进程service =========================

      /**
       * 发送任务给service执行
       */
      public static <T extends BaseCommandService> void sendCommand (
          Context context,
          Class<T> commandServiceClazz,
          int what) {

            Intent intent = new Intent(context, commandServiceClazz);

            Bundle bundle = new Bundle();
            bundle.putInt(KEY_COMMAND_WHAT, what);
            bundle.putBoolean(KEY_COMMAND_WHICH, true);
            intent.putExtra(KEY_COMMAND_EXTRA, bundle);
            context.startService(intent);
      }

      /**
       * 发送任务给service执行
       */
      public static <T extends BaseCommandService> void sendCommand (
          Context context,
          Class<T> commandServiceClazz,
          int what,
          Bundle bundle) {

            Intent intent = new Intent(context, commandServiceClazz);

            bundle.putInt(KEY_COMMAND_WHAT, what);
            intent.putExtra(KEY_COMMAND_EXTRA, bundle);
            context.startService(intent);
      }
}
