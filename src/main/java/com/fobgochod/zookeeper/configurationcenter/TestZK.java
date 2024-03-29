package com.fobgochod.zookeeper.configurationcenter;

import org.apache.zookeeper.ZooKeeper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;

/**
 * @author: 马士兵教育
 * @create: 2019-09-20 14:05
 */
public class TestZK {


    ZooKeeper zk;
    ZKConf zkConf;
    DefaultWatch defaultWatch;
    MyConf confMsg = new MyConf();
    WatchCallBack watchCallback = new WatchCallBack();

    @BeforeEach
    public void conn() {
        zkConf = new ZKConf();
        zkConf.setAddress("172.16.2.96:2181,172.16.2.97:2181,172.16.2.214:2181/config");
        zkConf.setSessionTime(1000);
        defaultWatch = new DefaultWatch();
        ZKUtils.setConf(zkConf);
        ZKUtils.setWatch(defaultWatch);
        zk = ZKUtils.getZK();
    }

    @AfterEach
    public void close() {
        ZKUtils.closeZK();
    }

    @Test
    public void getConfigFromZK() throws InterruptedException {

        //程序的配置来源：本地文件系统，数据库，redis，zk。。一切程序可以连接的地方
        //配置内容的提供、变更、响应：本地，数据库等等，都是需要心跳判断，或者手工调用触发

        //我是程序A 我需要配置：1，zk中别人是不是填充了配置；2，别人填充、更改了配置之后我怎么办

        watchCallback.setConfMsg(confMsg);
        watchCallback.setInit(1);
        watchCallback.setZk(zk);
        watchCallback.setWatchPath("/app");
        watchCallback.aWait();
        while (true) {
            if (confMsg.getConf() == null || "".equals(confMsg.getConf())) {
                System.err.println("conf diu le ....");
                watchCallback.aWait();
            } else {
                System.out.println(confMsg.getConf());
            }
            TimeUnit.SECONDS.sleep(2);
        }
    }

}
