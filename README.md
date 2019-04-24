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