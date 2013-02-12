package org.sample.spring.hello;

import org.apache.log4j.Logger;

/**
 * Logger service used via Spring AOP to log strings using log4j before and after execution
 * of {@linkplain GreetingService} methods.
 *  
 * @author milad
 */
public class GreetingLogger {

    protected static Logger logger = Logger.getLogger(GreetingLogger.class);
    
    public void before(GreetingService target) {
        logger.info("performing greeting (" + target.getName() + ")...");
    }

    public void after(GreetingService target) {
        logger.info("... (" + target.getName() + ") done.");
    }

}
