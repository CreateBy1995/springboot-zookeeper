package com.sunshine.springboot.zookeeper.main.watcher;

import com.sunshine.springboot.zookeeper.main.util.LogType;
import com.sunshine.springboot.zookeeper.main.util.LogUtil;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;

public class ConnectWatcher implements Watcher {
    @Override
    public void process(WatchedEvent event) {
        LogUtil.print("Zookeeper会话创建成功 当前连接服务器为: " ,
                this.getClass(), LogType.INFO);
    }
}
