package com.fobgochod.system.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

/**
 * SocketClient.java
 *
 * @author Xiao
 * @date 2021/8/5 17:03
 */
public class SocketPropertiesClient {

    public static void main(String[] args) {

        try {
            Socket client = new Socket("47.100.125.75", 9090);

            client.setSendBufferSize(20);
            client.setTcpNoDelay(false);
            client.setOOBInline(true);

            OutputStream out = client.getOutputStream();

            InputStream in = System.in;
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));

            while (true) {
                String line = reader.readLine();
                if (line != null) {
                    byte[] bb = line.getBytes();
                    for (byte b : bb) {
                        out.write(b);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
