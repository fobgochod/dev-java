package com.fobgochod.zookeeper.configurationcenter;

import org.apache.zookeeper.ZooKeeper;

import java.util.concurrent.CountDownLatch;

public class ZKUtils {

    private static ZooKeeper zk;
    private static ZKConf conf;
    private static DefaultWatch watch;
    private static CountDownLatch latch = new CountDownLatch(1);

    public static void setConf(ZKConf conf) {
        ZKUtils.conf = conf;
    }

    public static void setWatch(DefaultWatch watch) {
        watch.setInit(latch);
        ZKUtils.watch = watch;

    }

    public static ZooKeeper getZK() {
        try {
            zk = new ZooKeeper(conf.getAddress(), conf.getSessionTime(), watch);
            latch.await();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return zk;
    }

    public static void closeZK() {
        if (zk != null) {
            try {
                zk.close();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
