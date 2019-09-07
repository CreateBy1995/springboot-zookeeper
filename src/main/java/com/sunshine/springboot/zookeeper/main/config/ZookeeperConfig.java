package com.sunshine.springboot.zookeeper.main.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ZookeeperConfig {
    @Value("${zookeeper.serverAddress}")
    private String serverAddress ;
    @Value("${zookeeper.timeOut}")
    private Integer timeOut ;
    @Value("${zookeeper.connectWatcher}")
    private Class connectWatcher ;
    public Class getConnectWatcher() {
        return connectWatcher;
    }
    public Integer getTimeOut() {
        return timeOut;
    }
    public String getServerAddress() {
        return serverAddress;
    }
}
