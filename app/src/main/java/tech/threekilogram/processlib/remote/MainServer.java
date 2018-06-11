package tech.threekilogram.processlib.remote;

import tech.threekilogram.service.remote.BaseServerCore;
import tech.threekilogram.service.remote.BaseServerService;

/**
 * @author wuxio 2018-06-03:13:45
 */
public class MainServer extends BaseServerService {
    @Override
    protected BaseServerCore makeServerCore() {

        return new MainServerCore();
    }
}
