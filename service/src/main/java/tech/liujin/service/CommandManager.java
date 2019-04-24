package tech.liujin.service;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.util.SparseArrayCompat;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author wuxio 2018-06-11:17:03
 */
public class CommandManager {

      static final String KEY_COMMAND_TYPE              = "server.service.command.type";
      static final String KEY_COMMAND_WHAT              = "server.service.command.what";
      static final String KEY_COMMAND_BUNDLE            = "server.service.command.bundle";
      static final String KEY_COMMAND_LOCAL_EXTRA_INDEX = "server.service.local.command.runnable.index";

      static final int COMMAND_TYPE_LOCAL_WHAT_OBJECT = -3;
      static final int COMMAND_TYPE_LOCAL_OBJECT      = -2;
      static final int COMMAND_TYPE_LOCAL_RUNNABLE    = -1;
      static final int COMMAND_TYPE_NOT_DEFINE        = 0;
      static final int COMMAND_TYPE_WHAT              = 1;
      static final int COMMAND_TYPE_BUNDLE            = 2;
      static final int COMMAND_TYPE_WHAT_BUNDLE       = 3;

      /**
       * 为{@link #sTemp}提供线程安全的索引
       */
      private static AtomicInteger sLocalCommandIndexCalculate;

      /**
       * 用于存放本地明亮
       */
      private static SparseArrayCompat<Object> sTemp;

      /**
       * 初始化变量
       */
      private static void initFields ( ) {

            if( sTemp == null ) {
                  sTemp = new SparseArrayCompat<>();
            }
            if( sLocalCommandIndexCalculate == null ) {
                  sLocalCommandIndexCalculate = new AtomicInteger( 1 );
            }
      }

      /**
       * 用于{@link LocalCommandService} 根据索引读取任务
       *
       * @param index 索引
       *
       * @return 任务
       */
      static Object remove ( int index ) {

            if( sTemp == null ) {
                  return null;
            } else {
                  Object o = sTemp.get( index );
                  sTemp.remove( index );
                  return o;
            }
      }

      private static int add ( Object o ) {

            initFields();

            int index = sLocalCommandIndexCalculate.getAndAdd( 1 );
            sTemp.put( index, o );
            return index;
      }

      /**
       * 发送任务给service执行
       */
      public static <T extends LocalCommandService> void sendLocalCommand (
          Context context,
          Class<T> commandServiceClazz,
          int what ) {

            Intent intent = new Intent( context, commandServiceClazz );
            intent.putExtra( KEY_COMMAND_TYPE, COMMAND_TYPE_WHAT );
            intent.putExtra( KEY_COMMAND_WHAT, what );
            context.startService( intent );
      }

      /**
       * 发送任务给service执行
       */
      public static <T extends LocalCommandService> void sendLocalCommand (
          Context context,
          Class<T> commandServiceClazz,
          Object extra ) {

            int index = add( extra );
            Intent intent = new Intent( context, commandServiceClazz );
            intent.putExtra( KEY_COMMAND_TYPE, COMMAND_TYPE_LOCAL_OBJECT );
            intent.putExtra( KEY_COMMAND_LOCAL_EXTRA_INDEX, index );
            context.startService( intent );
      }

      /**
       * 发送任务给service执行
       */
      public static <T extends LocalCommandService> void sendLocalCommand (
          Context context,
          Class<T> commandServiceClazz,
          int what,
          Object extra ) {

            int index = add( extra );
            Intent intent = new Intent( context, commandServiceClazz );
            intent.putExtra( KEY_COMMAND_TYPE, COMMAND_TYPE_LOCAL_WHAT_OBJECT );
            intent.putExtra( KEY_COMMAND_WHAT, what );
            intent.putExtra( KEY_COMMAND_LOCAL_EXTRA_INDEX, index );
            context.startService( intent );
      }

      /**
       * 发送任务给service执行
       */
      public static <T extends LocalCommandService> void sendLocalCommand (
          Context context,
          Class<T> commandServiceClazz,
          Runnable runnable ) {

            int index = add( runnable );
            Intent intent = new Intent( context, commandServiceClazz );
            intent.putExtra( KEY_COMMAND_TYPE, COMMAND_TYPE_LOCAL_RUNNABLE );
            intent.putExtra( KEY_COMMAND_LOCAL_EXTRA_INDEX, index );
            context.startService( intent );
      }

      /**
       * 发送任务给service执行
       */
      public static <T extends RemoteCommandService> void sendRemoteCommand (
          Context context,
          Class<T> commandServiceClazz,
          int what ) {

            Intent intent = new Intent( context, commandServiceClazz );
            intent.putExtra( KEY_COMMAND_TYPE, COMMAND_TYPE_WHAT );
            intent.putExtra( KEY_COMMAND_WHAT, what );
            context.startService( intent );
      }

      /**
       * 发送任务给service执行
       */
      public static <T extends RemoteCommandService> void sendRemoteCommand (
          Context context,
          Class<T> commandServiceClazz,
          Bundle bundle ) {

            Intent intent = new Intent( context, commandServiceClazz );
            intent.putExtra( KEY_COMMAND_TYPE, COMMAND_TYPE_BUNDLE );
            intent.putExtra( KEY_COMMAND_BUNDLE, bundle );
            context.startService( intent );
      }

      /**
       * 发送任务给service执行
       */
      public static <T extends RemoteCommandService> void sendRemoteCommand (
          Context context,
          Class<T> commandServiceClazz,
          int what,
          Bundle bundle ) {

            Intent intent = new Intent( context, commandServiceClazz );
            intent.putExtra( KEY_COMMAND_TYPE, COMMAND_TYPE_WHAT_BUNDLE );
            intent.putExtra( KEY_COMMAND_WHAT, what );
            intent.putExtra( KEY_COMMAND_BUNDLE, bundle );
            context.startService( intent );
      }
}
