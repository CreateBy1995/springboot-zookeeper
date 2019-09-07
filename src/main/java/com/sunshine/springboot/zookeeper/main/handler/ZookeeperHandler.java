package com.sunshine.springboot.zookeeper.main.handler;

import com.sunshine.springboot.zookeeper.main.pojo.NodeValue;
import com.sunshine.springboot.zookeeper.main.util.ZookeeperUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ZookeeperHandler {
    @PostMapping("/createTemporaryNodeWithoutACL")
    public String createTemporaryNode(String path){
        NodeValue nodeValue = new NodeValue(1,"test") ;
        return ZookeeperUtil.createTemporaryNode(path,nodeValue) ;
    }
    @GetMapping("/getData")
    public  String getData(String path){
        NodeValue nodeValue = ZookeeperUtil.getData(path) ;
        return nodeValue.getContent() ;
    }
    @PostMapping("/setData")
    public  String setData(String path){
        NodeValue nodeValue = ZookeeperUtil.getData(path) ;
        nodeValue.setContent("update");
        return ZookeeperUtil.setData(path,nodeValue) ;
    }
    @PostMapping("/deleteData")
    public  String deleteData(String path){
        NodeValue nodeValue = ZookeeperUtil.getData(path) ;
        return ZookeeperUtil.deleteData(path,nodeValue) ;
    }
}
