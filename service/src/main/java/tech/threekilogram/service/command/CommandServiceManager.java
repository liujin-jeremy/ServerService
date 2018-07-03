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

      /**
       * 用于发送给{@link BaseInnerCommandService}的{@link ServiceCommand}位于{@link
       * #sRunnableArrayMap}的索引的key
       */
      static final String KEY_COMMAND_INDEX                = "tech.threekilogram.command.inner.service:";
      /**
       * {@link #sAtomicInteger} 起始索引
       */
      static final int    START_INDEX                      = 1010;
      /**
       * 用于标识{@link #sendCommand(Context, Class, int)}中what 的key
       */
      static final String KEY_COMMAND_WHAT                 = "tech.threekilogram.command.start.service:";
      /**
       * 用于标识{@link #sendCommand(Context, Class, int)}中没有额外的bundle信息 的key
       */
      static final String KEY_COMMAND_WITHOUT_BUNDLE_EXTRA = "tech.threekilogram.command.start.service.which:";
      /**
       * 用于标识{@link #sendCommand(Context, Class, int, Bundle)} 中 bundle 的key
       */
      static final String KEY_COMMAND_EXTRA                = "tech.threekilogram.command.start.extra.service:";
      /**
       * 临时放置传送给{@link BaseInnerCommandService}的{@link ServiceCommand}
       */
      private static ArrayMap<Integer, ServiceCommand> sRunnableArrayMap;
      /**
       * 为{@link #sRunnableArrayMap}提供线程安全的索引
       */
      private static AtomicInteger                     sAtomicInteger;

      // ========================= 本地service通信 =========================

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

      /**
       * 用于{@link BaseInnerCommandService} 根据索引读取任务
       *
       * @param index 索引
       *
       * @return 任务
       */
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
            bundle.putBoolean(KEY_COMMAND_WITHOUT_BUNDLE_EXTRA, true);
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
