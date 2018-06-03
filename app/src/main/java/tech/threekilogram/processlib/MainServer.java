package tech.threekilogram.processlib;

import tech.threekilogram.service.BaseServerCore;
import tech.threekilogram.service.BaseServerService;

/**
 * @author wuxio 2018-06-03:13:45
 */
public class MainServer extends BaseServerService {
    @Override
    protected BaseServerCore makeServerCore() {

        return new MainServerCore();
    }
}
