package com.fobgochod.zookeeper.locks;

import com.fobgochod.zookeeper.configurationcenter.DefaultWatch;
import com.fobgochod.zookeeper.configurationcenter.ZKConf;
import com.fobgochod.zookeeper.configurationcenter.ZKUtils;
import org.apache.zookeeper.ZooKeeper;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author: 马士兵教育
 * @create: 2019-09-20 16:14
 */
public class TestLock {

    private ZooKeeper zk;
    private ZKConf zkConf;
    private DefaultWatch defaultWatch;

    @Before
    public void conn() {
        zkConf = new ZKConf();
        zkConf.setAddress("172.16.2.96:2181,172.16.2.97:2181,172.16.2.214:2181/lock");
        zkConf.setSessionTime(1000);
        defaultWatch = new DefaultWatch();
        ZKUtils.setConf(zkConf);
        ZKUtils.setWatch(defaultWatch);
        zk = ZKUtils.getZK();
    }

    @After
    public void close() {
        ZKUtils.closeZK();
    }

    @Test
    public void testlock() {
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                WatchCallBack watchCallBack = new WatchCallBack();
                watchCallBack.setZk(zk);
                String name = Thread.currentThread().getName();
                watchCallBack.setThreadName(name);

                try {
                    //tryLock
                    watchCallBack.tryLock();
                    System.out.println(name + " at work");
                    watchCallBack.getRootData();
//                        Thread.sleep(1000);
                    //unLock
                    watchCallBack.unLock();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }).start();
        }


        while (true) {

        }
    }
}
