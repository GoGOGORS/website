package com.rx.config;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * RuiXiang
 * 2020-04-16-09:17
 * @author: RuiXiang
 */

@Slf4j
public class GateWayServer {

    private static ServerSocket SERVERSOCKET = null;

    static {
        try {
            SERVERSOCKET = new ServerSocket(8598);
            System.out.println("创建ServerSocket对象成功：prot:8598");
        } catch (IOException e) {
            log.error("创建ServerSocket对象失败" + e);
        }
    }

    /**
     * 次数统计
     */
    private static AtomicInteger flag = new AtomicInteger(1);

    /**
     * 监听网关
     */
    public static void startListenGateWay(){
        try {
            while (true) {
                Socket socket = SERVERSOCKET.accept();
                System.out.println("test：" + flag.getAndIncrement());

                SocketProcess.readData(socket);
                log.error("读取任务完成");
                SocketProcess.sendData(socket);
                log.error("发送任务完成");

            }

        } catch (IOException e) {
            log.error(e.toString());
        }

    }





}
