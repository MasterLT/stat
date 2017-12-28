package com.wasu.es.Thread;

import java.util.concurrent.CountDownLatch;

/**
 * Created by MASTER_L on 2017/12/11.
 */
public class BaseCountDowLatch implements Runnable {

    private CountDownLatch _latch;
    private String _serviceName;
    private boolean _serviceUp;

    public BaseCountDowLatch(String serviceName, CountDownLatch latch) {
        super();
        this._latch = latch;
        this._serviceName = serviceName;
        this._serviceUp = false;
    }

    @Override
    public void run() {
        try {
            if (doService())
                _serviceUp = true;
        } catch (Throwable t) {
            t.printStackTrace();
            _serviceUp = false;
        } finally {
            if (_latch != null) {
                _latch.countDown();
            }
        }
    }

    public String getServiceName() {
        return _serviceName;
    }

    public boolean isServiceUp() {
        return _serviceUp;
    }

    public boolean doService() {
        return true;
    }
}
