package com.rx.config;

import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * RuiXiang
 * 2020-04-20-11:05
 *
 * @author: RuiXiang
 */

@Slf4j
public class SocketProcess {

    /*private static InputStream inputStream = null;
    private static OutputStream outputStream = null;

    public SocketProcess(Socket socket) {
        try {
            inputStream = socket.getInputStream();
            outputStream = socket.getOutputStream();
        } catch (IOException e) {
            log.error("获取输入/输出流错误：" + e.getMessage());
        }
    }*/

    private static final ExecutorService POOL =  new ThreadPoolExecutor(5, 20,
            0L, TimeUnit.MICROSECONDS,
            new LinkedBlockingQueue<>(),
            new ThreadPoolExecutor.DiscardPolicy());


    /**
     * 接收数据线程
     */
    public static void readData(Socket socket) {
        InputStream inputStream = null;
        try {
            inputStream = socket.getInputStream();
        } catch (IOException e) {
            log.error("获取输入流错误：" + e.getMessage());
        }

        if (null != inputStream){
            InputStream dataInputStream = new DataInputStream(inputStream);
            POOL.execute(() -> {
                byte[] bytes = new byte[64];
                try {
                    dataInputStream.read(bytes);
                    StringBuilder builder = new StringBuilder();
                    for (byte b : bytes) {
                        builder.append(Integer.toHexString(b & 0xff)).append("-");
                    }

                    builder.replace(builder.length()-1, builder.length(), "");
                    log.info("收到数据：" + builder);
                    System.out.println("收到数据：" + builder);
                } catch (IOException e) {
                    log.error("读取信息错误：" + e);
                    System.out.println(e.toString());
                }
            });
        }

    }

    /**
     * 发送线程
     */
    public static void sendData(Socket socket){
        OutputStream outputStream = null;
        try {
            outputStream = socket.getOutputStream();
        } catch (IOException e) {
            log.error("获取输出流错误：" + e.getMessage());
        }

        if (null != outputStream){
            DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
            POOL.execute(() -> {
                try {
                    byte[] bytes = {0x08, 0x11, 0, 0, 0x3f, 0x68};
                    dataOutputStream.write(bytes, 0, bytes.length);
                    dataOutputStream.flush();
                    StringBuilder builder = new StringBuilder();
                    for (byte b : bytes) {
                        builder.append(Integer.toHexString(b & 0xff)).append("-");
                    }
                    System.out.println("send发送成功："+ builder.replace(builder.length()-1, builder.length(), ""));
                }catch (Exception e){
                    log.error("send信息错误：" + e);
                }

            });
        }

    }


    /**
     * 心跳检测
     * @param socket
     */
   /* public static void heartBeat(Socket socket) {
        try {
            OutputStream socketOutputStream = socket.getOutputStream();
            DataOutputStream outputStream = new DataOutputStream(socketOutputStream);
            POOL.execute(() -> {
                ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(3);
                scheduledExecutorService.scheduleWithFixedDelay(() -> {
                    byte[] bytes = {0x08, 0x11, 0, 0, 0x3f, 0x68};
                    try {
                        outputStream.write(bytes, 0, bytes.length);
                        outputStream.flush();
                        StringBuilder builder = new StringBuilder();
                        for (byte b : bytes) {
                            builder.append(Integer.toHexString(b & 0xff)).append("-");
                        }
                        System.out.println("心跳检测发送成功："+ builder.replace(builder.length()-1, builder.length(), ""));
                    } catch (IOException e) {
                        log.error("心跳检测异常：" + e.getMessage());
                    }

                }, 10000, 10000, TimeUnit.MILLISECONDS);
            });
        } catch (IOException e) {
            log.error("定时器获取输出流错误：" + e);
        }


    }*/


    /**
     * 用于流的关闭
     * @param closeables
     */
    private static void closeStream(Closeable... closeables){
        if (null != closeables){
            for (Closeable closeable : closeables) {
                if (null != closeable){
                    try {
                        closeable.close();
                    } catch (IOException e) {
                        // 忽略异常
                    }
                }
            }
        }
    }

}
