package com.sunshine.springboot.zookeeper.main.pojo;

import org.apache.zookeeper.data.Stat;

//@Data
//@AllArgsConstructor
//@NoArgsConstructor
public class NodeValue {
    private Integer id ;
    private String content ;
    /** 从服务器返回的节点stat中获取的version信息 不必参与序列化 */
    private transient Integer version;
    public NodeValue() {
    }

    public NodeValue(Integer id, String content) {
        this.id = id;
        this.content = content;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }
}
