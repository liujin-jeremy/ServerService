package tech.threekilogram.service.inner;

import java.io.Serializable;

/**
 * @author wuxio 2018-06-11:19:45
 */
public interface Command extends Runnable, Serializable {


    default Command newInstance() {

        return this;
    }
}
