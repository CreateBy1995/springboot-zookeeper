package com.sunshine.springboot.zookeeper.main.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogUtil {
    /**
     * @param msg  打印的信息
     * @param source  调用本方法的类
     */
    public static void info(String msg,Class source){
        Logger logger = LoggerFactory.getLogger(source) ;
        logger.info(msg);
    }
    public static void warn(String msg,Class source){
        Logger logger = LoggerFactory.getLogger(source) ;
        logger.warn(msg);
    }
    public static void error(String msg,Class source){
        Logger logger = LoggerFactory.getLogger(source) ;
        logger.error(msg);
    }
    /**
     * @param msg  打印的信息
     * @param source  调用本方法的类
     * @param logType 日志打印的类型
     */
    public static void print(String msg,Class source,LogType logType){
        Logger logger = LoggerFactory.getLogger(source) ;
        switch (logType){
            case INFO:
                logger.info(msg);
                break;
            case WARN:
                logger.warn(msg);
                break;
            case ERROR:
                logger.error(msg);
                break;
        }
    }
}
