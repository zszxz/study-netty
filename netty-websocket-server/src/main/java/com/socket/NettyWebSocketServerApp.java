package com.socket;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author lsc
 * <p>
 * </p>
 */
@SpringBootApplication
public class NettyWebSocketServerApp {

    public static void main(String[] args) {
        SpringApplication.run(NettyWebSocketServerApp.class ,args);
    }
}
