package dev.rmmarquini.aws.s3.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LoadLogger {

    public static Logger getLogger(String className) {
        return LogManager.getLogger(className);
    }

}
