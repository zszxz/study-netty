package com.zszxz.client;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.EventLoop;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * @Author lsc
 * <p> </p>
 */
@Slf4j
public class ConnectionListener implements ChannelFutureListener {
    private NettyClient nettyClient;
    public ConnectionListener(NettyClient nettyClient) {
        this.nettyClient = nettyClient;
    }
    @Override
    public void operationComplete(ChannelFuture channelFuture) throws Exception {
        if (!channelFuture.isSuccess()) {
            log.warn("-------------客户端重新连接-----------------");
            final EventLoop loop = channelFuture.channel().eventLoop();
            loop.schedule(new Runnable() {
                @SneakyThrows
                @Override
                public void run() {
                    nettyClient.connect(8080,"127.0.0.1");
                }
            }, 1L, TimeUnit.SECONDS);
        }
    }

}
