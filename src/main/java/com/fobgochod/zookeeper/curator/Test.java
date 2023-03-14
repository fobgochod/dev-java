package com.fobgochod.zookeeper.curator;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.Testable;
import org.apache.zookeeper.ZooKeeper;

public class Test {

    static String connectString = "172.16.2.96:2181,172.16.2.97:2181,172.16.2.214:2181";

    public static void main(String[] args) throws Exception {
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 0);
        CuratorFramework curator = CuratorFrameworkFactory.builder().connectString(connectString)
                .sessionTimeoutMs(60 * 1000)
                .connectionTimeoutMs(60 * 1000)
                .retryPolicy(retryPolicy)
                .build();
        curator.start();
        ZooKeeper zooKeeper = curator.getZookeeperClient().getZooKeeper();

        Testable testable = zooKeeper.getTestable();

        curator.blockUntilConnected();


        System.out.println("curator.getZookeeperClient().isConnected() = " + curator.getZookeeperClient().isConnected());
        curator.close();
    }
}
