package com.sunshine.springboot.zookeeper.main.watcher;

import com.sunshine.springboot.zookeeper.main.util.LogType;
import com.sunshine.springboot.zookeeper.main.util.LogUtil;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
/**
 * @Description:   获取节点时传入的监听器 如果子节点列表发生变化就会回调这个接口
 * 一般是根据业务情况临时创建 此处为了演示
 * @Author: 1995
 * @Date: 2019/9/9
 */
public class GetChildrenWatcher implements Watcher {
    @Override
    public void process(WatchedEvent event) {
        if (event.getType().getIntValue() == Event.EventType.NodeChildrenChanged.getIntValue()){
            LogUtil.print("子节点列表产生变化",this.getClass(), LogType.INFO);
        }
    }
}
