package com.sunshine.springboot.zookeeper.main.util;

import com.dyuproject.protostuff.LinkedBuffer;
import com.dyuproject.protostuff.ProtostuffIOUtil;
import com.dyuproject.protostuff.runtime.RuntimeSchema;
import com.sunshine.springboot.zookeeper.main.pojo.NodeValue;
import org.apache.zookeeper.*;
import org.apache.zookeeper.data.ACL;
import org.apache.zookeeper.data.Stat;

import java.util.List;

public class ZookeeperUtil {
    /** 客户端对象 */
    public static ZooKeeper zooKeeper ;
    /** 
     * @Description: 创建不需要权限的临时节点 临时节点不能拥有子节点
     * @param path 要创建的节点路径
     * @param nodeValue 节点对应的值
     * @return: 服务端的响应结果 也就是创建的节点路径
     * @Author: 1995 
     * @Date: 2019/9/8 
     */ 
    public static String createTemporaryNode(String path, NodeValue nodeValue){
        return syncCreate(path,nodeValue,ZooDefs.Ids.OPEN_ACL_UNSAFE,CreateMode.EPHEMERAL) ;
    }
    /**
     * @Description: 创建不需要权限的持久节点
     * @param path 要创建的节点路径
     * @param nodeValue 节点对应的值
     * @return: 服务端的响应结果 也就是创建的节点路径
     * @Author: 1995
     * @Date: 2019/9/8
     */
    public static String createPersistentNode(String path, NodeValue nodeValue){
        return syncCreate(path,nodeValue,ZooDefs.Ids.OPEN_ACL_UNSAFE,CreateMode.PERSISTENT) ;
    }
    public static void asyncCreateTemporaryNode(String path, NodeValue nodeValue,
                                                AsyncCallback.StringCallback callback,Object ctx){
        asyncCreate(path,nodeValue,ZooDefs.Ids.OPEN_ACL_UNSAFE,CreateMode.EPHEMERAL,callback,ctx) ;
    }
    /** 
     * @Description:  同步创建节点 也就是这个接口会等到服务端返回结果
     * @param path 要创建的节点路径
     * @param nodeValue 节点对应的值
     * @param acl 访问权限
     * @param createMode 创建的节点类型  持久/临时/顺序等
     * @return: 服务端的响应结果 也就是创建的节点路径
     * @Author: 1995 
     * @Date: 2019/9/7 
     */ 
    private static String syncCreate(String path, NodeValue nodeValue, List<ACL> acl, CreateMode createMode){
        byte[] nodeValueByte = serializeNodeValue(nodeValue) ;
        String result = null ;
        try {
            result = zooKeeper.create(path,nodeValueByte,acl,createMode) ;
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return result ;
    }
    /**
     * @Description:   异步创建节点  此接口不会抛出异常 如果有错误 会在回调函数中通过Result Code来体现
     * 也就是说调用此接口后会立即返回， 当服务端节点创建完毕后zk客户端会自动回调 callback中的processResult方法
     * @param path 要创建的节点路径
     * @param nodeValue 节点对应的值
     * @param acl 访问权限
     * @param createMode 创建的节点类型  持久/临时/顺序等
     * @param callback 回调函数
     * @param ctx 上下文对象 可以记录一些当前变量 供回调函数中使用
     * @Author: 1995
     * @Date: 2019/9/8
     */
    private static void asyncCreate(String path, NodeValue nodeValue, List<ACL> acl, CreateMode createMode,
                                     AsyncCallback.StringCallback callback,Object ctx){
        byte[] nodeValueByte = serializeNodeValue(nodeValue) ;
        zooKeeper.create(path,nodeValueByte,acl,createMode,callback,ctx) ;
    }
    /** 
     * @Description: 同步获取获取节点内容
     * @param path 要获取的节点路径
     * @param watcher 注册的监听器 如果节点发生变化会回调 监听器只生效一次
     * @return: 节点值对象
     * @Author: 1995 
     * @Date: 2019/9/7 
     */ 
    public static NodeValue getData(String path,Watcher watcher){
        byte[] nodeValueByte = null ;
        NodeValue nodeValue = null ;
        Stat stat = new Stat() ;
        try {
            // 将获取的值反序列化成一个对象并且返回
            // stat表示节点的状态 记录了dataVersion zxid 创建时间等等
            nodeValueByte = zooKeeper.getData(path,watcher, stat) ;
            nodeValue = deserializeNodeValue(nodeValueByte,NodeValue.class) ;
            nodeValue.setVersion(stat.getVersion());
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return nodeValue ;
    }
    /**
     * @Description:  更新节点的值同时返回该节点的当前版本  -1 表示出错
     * @param path 要更新的节点路径
     * @param nodeValue 要更新的节点值
     * @Author: 1995
     * @Date: 2019/9/8
     */
    public static String setData(String path,NodeValue nodeValue){
        byte[] nodeValueByte = serializeNodeValue(nodeValue) ;
        Stat stat = null ;
        String result = "500" ;
        try {
            stat = zooKeeper.setData(path,nodeValueByte,nodeValue.getVersion()) ;
            if(stat.getVersion() > 0){
                result = "200" ;
            }
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return result ;
    }
    /**
     * @Description:  删除节点
     * @param path 要删除的节点路径
     * @param nodeValue 要删除的节点值 用于获取版本值
     * @Author: 1995
     * @Date: 2019/9/8
     */
    public static String deleteData(String path,NodeValue nodeValue){
        byte[] nodeValueByte = serializeNodeValue(nodeValue) ;
        Stat stat = null ;
        String result = "500" ;
        try {
            zooKeeper.delete(path,nodeValue.getVersion()); ;
            result = "200" ;
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return result ;
    }
    /** 
     * @Description:
     * @param path 要删除的节点路径
     * @param watcher 注册的监听器 如果子节点列表发生变化会回调 监听器只生效一次
     * @return: 子节点列表的名称集合
     * @Author: 1995 
     * @Date: 2019/9/9 
     */ 
    public static List<String> getChildren(String path,Watcher watcher){
        List<String> childrenList = null ;
        try {
            childrenList = zooKeeper.getChildren(path,watcher);
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return childrenList ;
    }
    /**
     * @Description:  将对象序列化成字节数组
     * @param nodeValue 需要序列化的对象
     * @return: 序列化后的数组
     * @Author: 1995
     * @Date: 2019/9/7
     */
    private static <T> byte[] serializeNodeValue(T nodeValue){
        RuntimeSchema<T> schema = null ;
        LinkedBuffer buffer = null ;
        byte[] result = null ;
        try {
            schema = RuntimeSchema.createFrom((Class<T>)nodeValue.getClass());
            buffer = LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE);
            result = ProtostuffIOUtil.toByteArray(nodeValue, schema, buffer);
        } catch (Exception e) {
            throw new RuntimeException("serialize exception");
        } finally {
            if (buffer != null) {
                buffer.clear();
            }
        }
        return result;
    }
    /** 
     * @Description:  将字节数组反序列化成指定的类型
     * @param nodeValue 字节数组
     * @param typeClass 指定类型
     * @return: 反序列化后的对象
     * @Author: 1995 
     * @Date: 2019/9/7 
     */ 
    private static <T> T deserializeNodeValue(byte[] nodeValue, Class<T> typeClass) {
        RuntimeSchema<T> schema;
        T newInstance;
        try {
            schema = RuntimeSchema.createFrom(typeClass);
            newInstance = typeClass.newInstance();
            ProtostuffIOUtil.mergeFrom(nodeValue, newInstance, schema);

        } catch (Exception e) {
            throw new RuntimeException("deserialize exception");
        }
        return newInstance;
    }
}
