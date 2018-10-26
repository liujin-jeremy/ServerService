## 封装 Service 用于进程间通信

> 提供两种方式用于跨进程 service  操作

## 引入

Add it in your root build.gradle at the end of repositories:

```
	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```

**Step 2.** Add the dependency

```
	dependencies {
	        implementation 'com.github.threekilogram:ServerService:1.2.2'
	}
```

## BaseServerService

该类通过封装 Messenger 用于进程间通信,使用messenger用于两端发送消息,两端根据消息的不同,触发各种逻辑事件

###  示例

1 实现服务端service逻辑类

```
// 集成 BaseServerCore 该类用于服务端处理服务端逻辑
public class MainServerCore extends BaseServerCore {

      private static final String TAG = "MainServerCore";

      @Override
      protected void onCreate (Context context) {

            /* 服务端service 创建时回调,相当于service onCreate */

            Log.i(TAG, "onCreate:" + "");
            super.onCreate(context);
      }

      @Override
      protected void onStart (Intent intent) {

            /* 服务端service和客户端连接了,可以通信了,相当于 service onBind */

            Log.i(TAG, "onStart:" + "");
      }

      @Override
      protected void onStop (Intent intent) {

            /* 服务端service和客户端断开连接了,相当于 service unBind */

            Log.i(TAG, "onStop:" + "");
            super.onStop(intent);
      }

      @Override
      protected void onDestroy () {

            /* 服务端service 销毁时回调,相当于service onDestroy */

            Log.i(TAG, "onDestroy:" + "");
            super.onDestroy();
      }

      /**
       * @param msg 消息
       */
      @Override
      protected void handleMessage (Message msg) {

            /* 此处处理服务端发送来的消息 */

            Log.i(TAG, "handleMessage:" + msg.what);

            try {
                  //测试:接收到消息之后就发送回客户端
                  //该类提供了向客户端发行消息的方法
                  sendMessageToClient(msg.what);
            } catch(RemoteException e) {
                  e.printStackTrace();
            }
      }
}
```

2 实现服务端service

```
// 该server主要用于 manifest 跨进程,主要逻辑功能通过 BaseServerCore 的实现类实现
public class MainServer extends BaseServerService {

    @Override
    protected BaseServerCore makeServerCore() {
		
		//该方法返回服务端主要逻辑
        return new MainServerCore();
    }
}
```

```
<service
        android:enabled="true"
        android:name=".remote.MainServer"
        android:process=":server"/>  ->> 指定进程
```

3 实现客户端逻辑

```
public class MainClientCore extends BaseClientCore {

      private static final String TAG = "MainClientCore";

      @Override
      protected void onStart (ComponentName name) {

            /* 和服务端已经连接了 */

            Log.i(TAG, "onStart:" + name.toShortString());
      }

      @Override
      protected void onFinish (ServerConnection connection) {

            /* 主动和服务端断开连接了 */

            Log.i(TAG, "onFinish:" + "");
      }

      @Override
      protected void onServiceDisconnected (ComponentName name) {

            /* 意外断开连接了 */

            Log.i(TAG, "onServiceDisconnected:" + name.toShortString());
      }

      @Override
      protected void handleMessage (Message msg) {

            /* 此处可以接收服务端消息,根据服务端消息处理不同事件 */

            Log.i(TAG, "handleMessage:" + msg.what);
      }
}
```

4 开始通信

```
private ServerConnection mConnection;
private MainClientCore   mCore;

//先创建一个连接辅助类
mConnection = new ServerConnection();
//建立连接
mConnection.connectToServer(this, mCore, MainServer.class);

================================================================================================

//断开连接
mConnection.disConnectToServer(this);

================================================================================================

//客户端向服务端发送消息
try {
    mCore.sendMessageToServer(12);
} catch (RemoteException e) {
    e.printStackTrace();
}

================================================================================================

//服务端向客户端发送消息
BaseServerCore#sendMessageToClient(int)
```

![log](/img/img00.gif)

## BaseCommandService

该类可以接收CommandServiceManager发送的消息,根据消息触发不同的逻辑事件

1 实现service

```
public class MainCommandService extends BaseCommandService {

      private static final String TAG = "MainCommandService";

      /**
       * 实现该方法,绑定消息处理类到service
       *
       * @return 消息接收类
       */
      @Override
      protected CommandReceiver createCommandReceiver () {

            return new Receiver();
      }

      /**
       * 该类接收收到的消息,根据消息进行逻辑处理
       */
      private class Receiver implements CommandReceiver {

            @Override
            public void onCommandReceive (int what) {

                  Log.i(TAG, "onCommandReceive:" + what);
            }

            @Override
            public void onCommandReceive (int what, Bundle bundle) {

                  Log.i(TAG, "onCommandReceive:" + what + " " + bundle);
            }
      }
}
```

```
<service
    android:enabled="true"
    android:name=".start.MainCommandService"
    android:process=":test"
    />
```

2 通信

```
//发送一个简单的消息
CommandServiceManager.sendCommand(this, MainCommandService.class, 12);
```

```
//发送一个带bundle的消息
<<<<<<< HEAD
Bundle bundle = new Bundle();
bundle.putString("temp", "Hello");
CommandServiceManager.sendCommand(this, MainCommandService.class, 12, bundle);
```

log:

```
tech.threekilogram.processlib:test I/MainCommandService: onCommandReceive:12
tech.threekilogram.processlib:test I/MainCommandService: onCommandReceive:12 Bundle[{tech.threekilogram.command.start.service:=12, temp=Hello}]
```

=======
CommandServiceManager.sendCommand(this, MainCommandService.class, 12, new Bundle());
```
>>>>>>> b48f3c8c8222ceeaffabb17114e95ffaaabc03fd
