package com.zszxz.netty.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * @Author lsc
 * <p>netty server 端 </p>
 */
@Component
public class NettyServer {

    private Integer port = 8080;

    // 配置线程组 实质是 reactor线程组
    NioEventLoopGroup parentGroup = new NioEventLoopGroup();
    // 配置线程组
    NioEventLoopGroup childGroup = new NioEventLoopGroup();

    @Autowired
    ChildChannelHandler childChannelHandler;

    @PostConstruct
    public void init() throws Exception{


        // 启动 NIO
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        //
        serverBootstrap.group(parentGroup,childGroup)
                .channel(NioServerSocketChannel.class)// 相当于 ServerSocketChannel
                .option(ChannelOption.SO_BACKLOG,1024)//TCP参数 1024 个队列
                .childHandler(childChannelHandler);// 处理事件
        // 绑定端口 同步阻塞等待同步成功 channelFuture 异步操作通知回调
        ChannelFuture channelFuture = serverBootstrap.bind(port).sync();
        // 同步阻塞等待服务监听端口关闭
        channelFuture.channel().closeFuture().sync();


    }



    @PreDestroy
    public void destory() throws InterruptedException {
        // 关闭资源
        parentGroup.shutdownGracefully().sync();
        childGroup.shutdownGracefully().sync();
    }





}
