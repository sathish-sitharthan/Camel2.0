package com.lykos.quickfix;


import java.util.concurrent.CountDownLatch;

import org.apache.camel.Exchange;
import org.apache.camel.Handler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CountDownLatchDecrementer {
    private static final Logger LOG = LoggerFactory.getLogger(CountDownLatchDecrementer.class);

    private String label;
    private CountDownLatch latch;
    
    public CountDownLatchDecrementer(String label, CountDownLatch latch) {
        this.label = label;
        this.latch = latch;
    }

    @Handler
    public void decrement(Exchange exchange) {
        LOG.info("Decrementing latch count: " + label);
        latch.countDown();
    }
}
