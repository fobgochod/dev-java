package com.fobgochod.zookeeper.lock;

import com.fobgochod.zookeeper.configurationcenter.DefaultWatch;
import com.fobgochod.zookeeper.configurationcenter.ZKConf;
import com.fobgochod.zookeeper.configurationcenter.ZKUtils;
import org.apache.zookeeper.ZooKeeper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * @author: 马士兵教育
 * @create: 2019-09-20 21:21
 */
public class TestLock {

    private ZooKeeper zk;
    private ZKConf zkConf;
    private DefaultWatch defaultWatch;

    @BeforeEach
    public void conn() {
        zkConf = new ZKConf();
        zkConf.setAddress("172.16.2.96:2181,172.16.2.97:2181,172.16.2.214:2181/lock");
        zkConf.setSessionTime(1000);
        defaultWatch = new DefaultWatch();
        ZKUtils.setConf(zkConf);
        ZKUtils.setWatch(defaultWatch);
        zk = ZKUtils.getZK();
    }

    @AfterEach
    public void close() {
        try {
            zk.close();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void lock() {
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                WatchCallBack watchCallBack = new WatchCallBack();
                watchCallBack.setZk(zk);
                String threadName = Thread.currentThread().getName();
                watchCallBack.setThreadName(threadName);
                //每一个线程：
                //抢锁
                watchCallBack.tryLock();
                //干活
                System.out.println(threadName + " working...");
                // try {
                //     Thread.sleep(1000);
                // } catch (InterruptedException e) {
                //     e.printStackTrace();
                // }
                //释放锁
                watchCallBack.unLock();
            }).start();
        }

        while (true) {
        }
    }
}
