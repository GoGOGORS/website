package com.rx.job;

import com.rx.config.GateWayServer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * RuiXiang
 * 2020-04-20-15:53
 *
 * @author: RuiXiang
 */

@Slf4j
@Configuration
@EnableScheduling
public class HeartBeatJob {

    // @Scheduled(fixedRate = 10000)
    public void test() throws IOException {
        Socket socket = GateWayServer.socket;

        if (null != socket){
            DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
            try {
                byte[] bytes = {0x08, 0x11, 0, 0, 0x3f, 0x68};
                dataOutputStream.write(bytes, 0, bytes.length);
                dataOutputStream.flush();
                StringBuilder builder = new StringBuilder();
                for (byte b : bytes) {
                    builder.append(Integer.toHexString(b & 0xff)).append("-");
                }
                System.out.println("心跳检测发送成功："+ builder.replace(builder.length()-1, builder.length(), ""));
            }catch (Exception e){
                log.error("心跳检测发送错误：" + e);
            }

        }

    }


}
