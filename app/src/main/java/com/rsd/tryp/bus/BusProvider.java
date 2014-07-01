package com.rsd.tryp.bus;

import com.squareup.otto.Bus;

/**
 * Created by wadereweti on 2/07/14.
 */
public class BusProvider {
    private static final Bus BUS = new Bus();

    public static Bus getInstance() {
        return BUS;
    }

    private BusProvider(){}
}
