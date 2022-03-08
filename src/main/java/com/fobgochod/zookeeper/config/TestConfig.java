package com.fobgochod.zookeeper.config;

import org.apache.zookeeper.ZooKeeper;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

/**
 * @author: 马士兵教育
 * @create: 2019-09-20 20:07
 */
public class TestConfig {

    private ZooKeeper zk;

    @Before
    public void conn() {
        zk = ZKUtils.getZK();
    }

    @After
    public void close() {
        try {
            zk.close();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void getConfigFromZK() throws InterruptedException {

        WatchCallBack watchCallBack = new WatchCallBack();
        watchCallBack.setZk(zk);
        MyConf myConf = new MyConf();
        watchCallBack.setConf(myConf);

        watchCallBack.aWait();
        //1，节点不存在
        //2，节点存在

        while (true) {
            if ("".equals(myConf.getConf())) {
                System.out.println("conf diu le ......");
                watchCallBack.aWait();
            } else {
                System.out.println(myConf.getConf());
            }
            TimeUnit.SECONDS.sleep(2);
        }
    }
}
