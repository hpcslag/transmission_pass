/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tp.transfer;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class LetServer {

    public static int onlineCount = 0;

    LetServer() {
        new Thread() {

            public void run() {
                while (true) {
                    try {
                        System.err.println("目前線上人數：" + onlineCount);
                        Thread.sleep(1000);
                    } catch (Exception e) {
                    }
                }
            }
        }.start();
    }

    public static void main(String[] args) throws Exception {
        new LetServer();

        ServerSocket ss = new ServerSocket(3333);
        System.out.println("伺服器已啟動...");

        while (true) {
            Socket s = ss.accept();
            Thread t = new Thread(new MyThread(s));
            t.start();
        }
    }
}

class MyThread extends Thread {

    Socket s;

    MyThread(Socket s) {
        this.s = s;
    }

    @Override
    public void run() {
        LetServer.onlineCount++;
        try {
            System.out.println(s.getRemoteSocketAddress() + "　已連線...");
            BufferedReader r = new BufferedReader(new InputStreamReader(s.getInputStream()));
            PrintWriter w = new PrintWriter(new OutputStreamWriter(s.getOutputStream()), true);

            while (true) {
                w.println("你好．．．我是伺服器，這是伺服器的訊息");
                System.out.println(s.getRemoteSocketAddress() + " 傳遞的訊息是：" + r.readLine());
            }

        } catch (Exception e) {
            System.err.println(s.getRemoteSocketAddress() + " 已離線");
        }
        LetServer.onlineCount--;
    }
}