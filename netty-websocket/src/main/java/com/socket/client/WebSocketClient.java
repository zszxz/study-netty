package com.socket.client;

import com.socket.handler.ClientHandler;
import com.socket.handler.ClientInitializer;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.DefaultHttpHeaders;
import io.netty.handler.codec.http.websocketx.WebSocketClientHandshakerFactory;
import io.netty.handler.codec.http.websocketx.WebSocketVersion;
import lombok.extern.slf4j.Slf4j;

import java.net.URI;

/**
 * @Author lsc
 * <p> </p>
 */
@Slf4j
public class WebSocketClient {



    private URI uri;

    public WebSocketClient(URI uri){
        this.uri = uri;
    }

    public void connection(){
        EventLoopGroup eventLoopGroup = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            // 创建handler
            ClientHandler webSocketClientHandler = new ClientHandler(
                    WebSocketClientHandshakerFactory.newHandshaker(uri
                            , WebSocketVersion.V13
                            , null
                            , false
                            , new DefaultHttpHeaders()),new WebSocketClient(uri));
            // 线程组 设置 handler
            bootstrap.group(eventLoopGroup)
                    .channel(NioSocketChannel.class)
                    .handler(new ClientInitializer(webSocketClientHandler));
            // 辅助启动
            Channel channel = bootstrap
                    .connect(uri.getHost(), uri.getPort())// 获取地址
                    .sync()
                    .channel();
            // 优雅关闭
            channel.closeFuture().sync();
        } catch (InterruptedException e) {
            log.error("socket连接异常:{}",e.getMessage());
            e.printStackTrace();
        } finally {
            eventLoopGroup.shutdownGracefully();
        }
    }

}
