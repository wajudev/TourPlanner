package com.example.tourplanner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class OtherApp {
    private static Logger logger = LogManager.getLogger(OtherApp.class);

    public void test(){
       logger.debug("Do some tests....");
    }
}
