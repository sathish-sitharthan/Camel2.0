package com.lykos.quickfix;

import org.apache.camel.Exchange;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import quickfix.ConfigError;

public class QuickfixjMessageJsonPrinter {

    private static final Logger LOG = LoggerFactory.getLogger(QuickfixjMessageJsonPrinter.class);
    private QuickfixjEventJsonTransformer formatter;

    public QuickfixjMessageJsonPrinter() throws ConfigError {
        formatter = new QuickfixjEventJsonTransformer();
    }
    
    public void print(Exchange exchange) {
        LOG.info(formatter.transform(exchange));
    }
}
