package com.socket.server;

import com.socket.handler.SocketChannelInitializer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.extern.slf4j.Slf4j;

/**
 * @author lsc
 * <p> </p>
 */
@Slf4j
public class WebSocketServer {

    public void init(){
        NioEventLoopGroup boss=new NioEventLoopGroup();
        NioEventLoopGroup work=new NioEventLoopGroup();
        try {
            ServerBootstrap bootstrap=new ServerBootstrap();
            bootstrap.group(boss,work);
            bootstrap.channel(NioServerSocketChannel.class);
            bootstrap.childHandler(new SocketChannelInitializer());
            Channel channel = bootstrap.bind(8096).sync().channel();
            log.info("------------webSocket服务器启动成功-----------："+channel);
            channel.closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
            log.info("---------运行出错----------："+e);
        }finally {
            boss.shutdownGracefully();
            work.shutdownGracefully();
            log.info("------------websocket服务器已关闭----------------");
        }
    }
}
