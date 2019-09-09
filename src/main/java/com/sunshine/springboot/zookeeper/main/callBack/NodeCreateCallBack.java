package com.sunshine.springboot.zookeeper.main.callBack;

import com.sunshine.springboot.zookeeper.main.util.LogType;
import com.sunshine.springboot.zookeeper.main.util.LogUtil;
import org.apache.zookeeper.AsyncCallback;

public class NodeCreateCallBack implements AsyncCallback.StringCallback {
    /** 
     * @Description:  节点创建成功的回调接口 此处为了演示才创建一个类 一般是根据业务临时创建一个callback
     * @param rc 0 : 接口调用成功 ， -4 : 客户端和服务端连接断开 -110 : 节点已存在 -112 : 会话过期
     * @param path 要创建的节点路径
     * @param ctx 调用异步创建节点时传入的上下文
     * @param name 实际上在服务端创建的节点名 因为有可能是顺序节点 所以名称可能和传入的不一样
     * @return: void 
     * @Author: 1995 
     * @Date: 2019/9/8 
     */ 
    @Override
    public void processResult(int rc, String path, Object ctx, String name) {
        switch (rc){
            case 0:
                LogUtil.print("节点"+path+"创建成功",this.getClass(), LogType.INFO);
                break;
            case -4:
                LogUtil.print("客户端和服务端连接断开",this.getClass(), LogType.ERROR);
                break;
            case -110:
                LogUtil.print("节点"+path+"已存在",this.getClass(), LogType.ERROR);
                break;
            case -112:
                LogUtil.print("会话过期",this.getClass(), LogType.ERROR);
                break;
        }
    }
}
