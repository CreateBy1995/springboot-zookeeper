package com.sunshine.springboot.zookeeper.main.lifeCycle;

import com.sunshine.springboot.zookeeper.main.config.ZookeeperConfig;
import com.sunshine.springboot.zookeeper.main.util.LogType;
import com.sunshine.springboot.zookeeper.main.util.LogUtil;
import com.sunshine.springboot.zookeeper.main.util.ZookeeperUtil;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.SmartLifecycle;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class ZookeeperUtilFactory implements SmartLifecycle {
    @Autowired
    private ZookeeperConfig zookeeperConfig ;  // zookeeper配置类
    private boolean isRunning = false;  // SpringBoot是否启动的标志位
    /**
     * 该方法在SpringBoot启动的时候被回调
     */
    @Override
    public void start() {
        isRunning = true ;
        createZookeeperUtil();
    }

    @Override
    public void stop() {

    }

    /**
     * 该方法在SpringBoot关闭的时候被回调
     * 如果isRunning返回true,则本方法会被调用
     * 业务逻辑处理结束后要调用 callback.run()。
     * 否则在程序退出时，SpringBoot的DefaultLifecycleProcessor会认为stop()没有完成，程序会一直卡着结束不了
     * 等待一定时间 默认超时时间30000ms 后才会自动结束。
     */

    @Override
    public void stop(Runnable callback) {
        LogUtil.print("在SpringBoot 关闭的时候做一些事情",this.getClass(), LogType.ERROR);
        callback.run();
    }

    /**
     * 在调用start()方法前会先判断 返回值 如果为false 才会调用
     * 在调用stop()方法前会先判断 返回值 如果为true 才会调用
     * @return
     */
    @Override
    public boolean isRunning() {
        return isRunning;
    }
    /** 
     * @Description: 创建Zookeeper客户端连接
     * @Author: 1995
     * @Date: 2019/9/7 
     */ 
    public void createZookeeperUtil(){
        ZooKeeper zooKeeper = null ;
        try {
            // 客户端连接成功的回调事件
            Watcher watcher = (Watcher)zookeeperConfig.getConnectWatcher().newInstance();
            // 服务器地址列表
            String serverAddress = zookeeperConfig.getServerAddress() ;
            // 客户端连接超时时间
            Integer timeOut = zookeeperConfig.getTimeOut() ;
            LogUtil.print("准备连接Zookeeper服务器 --- 服务器地址为: "+serverAddress+" --- 超时时间为: "+timeOut+"ms" ,
                    this.getClass(), LogType.INFO);
            zooKeeper = new ZooKeeper(serverAddress,timeOut,watcher) ;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
        ZookeeperUtil.zooKeeper = zooKeeper ;
    }
}
