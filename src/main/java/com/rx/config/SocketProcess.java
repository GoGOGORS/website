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
            byte[] b = new byte[1];
            while (-1 !=(inputStream.read(b))){
                System.out.println(Integer.toHexString(b[0]));

                switch (b[0]){
                    case (byte) 0xff: break;
                    //认证
                    case (byte) 0xfe: authentication(socket, inputStream); break;
                    case (byte) 0x12: break;
                    default:
                        processData(socket, inputStream);
                        break;
                }

            }

        } catch (IOException e) {
            log.error("获取输入流错误：" + e.getMessage());
        }

    }

    private static void processData(Socket socket, InputStream inputStream) {


    }


    /**
     * 认证
     * @param socket
     * @param inputStream
     */
    private static void authentication(Socket socket, InputStream inputStream) {
        byte[] bytes = new byte[35];
        try {
            while (-1 != (inputStream.read(bytes))){
                StringBuilder stringBuilder = new StringBuilder();
                StringBuilder stringBuilder2 = new StringBuilder();
                StringBuilder sb = new StringBuilder();
                StringBuilder sb2 = new StringBuilder();
                int flag = 0;
                for (byte b : bytes) {
                    stringBuilder2.append(Integer.toHexString(b));
                    sb2.append(Integer.toHexString(bytes[flag]&0xff));
                    flag++;
                }

                System.out.println("stringBuilder2" + stringBuilder2);
                System.out.println("sb2" + sb2);
                for (int i = 0; i < bytes.length; i++) {
                   if (0 == i){
                       stringBuilder.append(Integer.toHexString(bytes[i]));
                       sb.append(Integer.toHexString(bytes[i]&0xff));
                   }else if (i > 0){
                       stringBuilder.append("-").append(Integer.toHexString(bytes[i]&0xff));
                       sb.append("-"+Integer.toHexString(bytes[i]&0xff));
                   }
                }
                System.out.println("===========");
                System.out.println("stringBuilder" + stringBuilder);
                System.out.println("sb" + sb);


            }
        }catch (Exception e){
            e.printStackTrace();
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
