package com.socket.config;

import com.socket.client.WebSocketClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * @Author lsc
 * <p> </p>
 */
@Slf4j
@Component
public class ApplicationConfig implements ApplicationListener<ApplicationReadyEvent> {



    @Override
    public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {
        System.out.println("客户端启动");
        // webscoket 地址
        String addr = "ws://127.0.0.1:8096";
        URI uri = null;
        try {
            uri = new URI(addr);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        WebSocketClient webSocketClient = new WebSocketClient(uri);
        webSocketClient.connection();

    }
}
