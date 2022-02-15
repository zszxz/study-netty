package com.socket.config;

import com.socket.server.WebSocketServer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * @Author lsc
 * <p> </p>
 */
@Slf4j
@Component
public class ApplicationConfig implements ApplicationListener<ApplicationReadyEvent> {



    @Override
    public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {
        WebSocketServer webSocketServer = new WebSocketServer();
        webSocketServer.init();
    }
}
