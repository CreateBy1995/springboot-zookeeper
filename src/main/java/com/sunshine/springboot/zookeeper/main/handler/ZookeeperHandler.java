package com.sunshine.springboot.zookeeper.main.handler;

import com.sunshine.springboot.zookeeper.main.callBack.NodeCreateCallBack;
import com.sunshine.springboot.zookeeper.main.context.NodeCreateContext;
import com.sunshine.springboot.zookeeper.main.pojo.NodeValue;
import com.sunshine.springboot.zookeeper.main.util.LogType;
import com.sunshine.springboot.zookeeper.main.util.LogUtil;
import com.sunshine.springboot.zookeeper.main.util.ZookeeperUtil;
import com.sunshine.springboot.zookeeper.main.watcher.GetChildrenWatcher;
import com.sunshine.springboot.zookeeper.main.watcher.GetDataWatcher;
import org.apache.zookeeper.AsyncCallback;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ZookeeperHandler {
    @PostMapping("/createTemporaryNodeWithoutACL")
    public String createTemporaryNode(String path){
        NodeValue nodeValue = new NodeValue(1,"test") ;
        return ZookeeperUtil.createTemporaryNode(path,nodeValue) ;
    }
    @PostMapping("/createPersistentNodeWithoutACL")
    public String createPersistentNode(String path){
        NodeValue nodeValue = new NodeValue(1,"test") ;
        return ZookeeperUtil.createPersistentNode(path,nodeValue) ;
    }
    @GetMapping("/getData")
    public  String getData(String path){
        NodeValue nodeValue = ZookeeperUtil.getData(path,new GetDataWatcher()) ;
        return nodeValue.getContent() ;
    }
    @PostMapping("/setData")
    public  String setData(String path){
        NodeValue nodeValue = ZookeeperUtil.getData(path,null) ;
        nodeValue.setContent("update");
        return ZookeeperUtil.setData(path,nodeValue) ;
    }
    @PostMapping("/deleteData")
    public  String deleteData(String path){
        NodeValue nodeValue = ZookeeperUtil.getData(path,null) ;
        return ZookeeperUtil.deleteData(path,nodeValue) ;
    }
    @GetMapping("/getChildren")
    public  Integer getChildren(String path){
        List<String> lists  = ZookeeperUtil.getChildren(path,new GetChildrenWatcher()) ;
        return lists.size();
    }
    @PostMapping("/asyncCreateTemporaryNodeWithoutACL")
    public  void asyncCreateTemporaryNodeWithoutACL(String path){
        NodeValue nodeValue = new NodeValue(1,"test") ;
        NodeCreateContext nodeCreateContext = new NodeCreateContext();
        NodeCreateCallBack nodeCreateCallBack = new NodeCreateCallBack() ;
        ZookeeperUtil.asyncCreateTemporaryNode(path,nodeValue,nodeCreateCallBack,nodeCreateContext );
    }
}
