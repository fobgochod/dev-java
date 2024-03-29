package com.fobgochod.system.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketBIO {

    public static void main(String[] args) throws Exception {
        ServerSocket server = new ServerSocket(9090, 20);

        System.out.println("step1: new ServerSocket(9090) ");

        while (true) {
            // 阻塞1
            Socket client = server.accept();
            System.out.println("step2:client\t" + client.getPort());

            new Thread(() -> {
                InputStream in;
                try {
                    in = client.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                    while (true) {
                        // 阻塞2
                        String dataline = reader.readLine();

                        if (dataline == null || "exit".equals(dataline)) {
                            client.close();
                            break;
                        } else {
                            println(Thread.currentThread(), dataline);
                        }
                    }
                    println(Thread.currentThread(), "客户端断开");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }

    public static void println(Thread thread, String msg) {
        System.out.printf("[%s]: [%s]\n", thread.getName(), msg);
    }
}
