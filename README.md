## 封装 Service 用于进程间通信

> 提供两种方式用于跨进程 service  操作

## 引入

```
implementation 'tech.liujin:service:1.0.0'
```

## 使用

### 本地service

```
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
```

```
<service
    android:enabled="true"
    android:name=".LocalService"/>
```

发送命令

```
CommandManager.sendLocalCommand( this, LocalService.class, 2 );
```

发送消息

```
String s = "Hello World";
CommandManager.sendLocalCommand( this, LocalService.class, s );
```

发送命令/消息

```
CommandManager.sendLocalCommand( this, LocalService.class, 3, "Hello Local Service" );
```

发送程序

```
CommandManager.sendLocalCommand( this, LocalService.class, new Runnable() {
      @Override
      public void run ( ) {
            Log.i( TAG, "run: " + " 运行一段程序" );
      }
} );
```



### 跨进程Service

```
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
```

```
<service
    android:enabled="true"
    android:name=".RemoteService"
    android:process=":test"/>
```

发送命令

```
CommandManager.sendRemoteCommand( this, RemoteService.class, 0 );
```

发送参数

```
Bundle bundle = new Bundle();
bundle.putString( "temp", "Hello" );
CommandManager.sendRemoteCommand( this, RemoteService.class, bundle );
```

发送命令/参数

```
Bundle bundle = new Bundle();
bundle.putString( "temp", "Hello" );
CommandManager.sendRemoteCommand( this, RemoteService.class, 1, bundle );
```

