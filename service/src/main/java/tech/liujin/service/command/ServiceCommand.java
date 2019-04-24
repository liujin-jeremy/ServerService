package tech.liujin.service.command;

import android.app.Service;
import android.content.Context;

/**
 * 该接口表示一段逻辑用于service执行
 *
 * @author: Liujin
 * @version: V1.0
 * @date: 2018-07-03
 * @time: 11:16
 */
public interface ServiceCommand {

      /**
       * 该类用于{@link CommandServiceManager#sendCommand(Context, Class, ServiceCommand)}
       *
       * @param service 执行该接口的service,主要用于提供context
       */
      void run ( Service service );
}
