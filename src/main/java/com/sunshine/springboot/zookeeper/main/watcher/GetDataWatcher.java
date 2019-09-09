package com.sunshine.springboot.zookeeper.main.watcher;

import com.sunshine.springboot.zookeeper.main.util.LogType;
import com.sunshine.springboot.zookeeper.main.util.LogUtil;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;

/**
 * @Description:   获取节点时传入的监听器 如果节点发生变化就会回调这个接口
 * 一般是根据业务情况临时创建 此处为了演示
 * @Author: 1995
 * @Date: 2019/9/9 
 */ 
public class GetDataWatcher implements Watcher{
    @Override
    public void process(WatchedEvent event) {
        if (event.getType().getIntValue() == Event.EventType.NodeDataChanged.getIntValue()){
            LogUtil.print("节点数据更新",this.getClass(), LogType.INFO);
        }else if(event.getType().getIntValue() == Event.EventType.NodeDeleted.getIntValue()){
            LogUtil.print("节点被删除",this.getClass(), LogType.INFO);
        }
    }
}
