package com.zszxz.beat.service;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

/**
 * @Author lsc
 * <p> </p>
 */
public class NettyServer {

    public static void main(String[] args) throws Exception {
        NettyServer nettyServer = new NettyServer();
        // 连接的ip d端口
        nettyServer.bind(8080);
    }

    public void bind(int port) throws Exception{

        // 配置线程组 实质是 reactor线程组
        NioEventLoopGroup parentGroup = new NioEventLoopGroup();
        // 启动 NIO
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        // 启动类
        serverBootstrap.group(parentGroup)
                .channel(NioServerSocketChannel.class)// 相当于 ServerSocketChannel
                .option(ChannelOption.SO_BACKLOG,1024)//TCP参数
                .childHandler(new ChildChannelHandler());// 处理事件
        // 绑定端口 同步阻塞等待同步成功 channelFuture 异步操作通知回调
        ChannelFuture channelFuture = serverBootstrap
                .bind(port)
                .sync();
        // 同步阻塞等待服务监听端口关闭
        channelFuture
                .channel()
                .closeFuture()
                .sync();
        // 关闭资源
        parentGroup.shutdownGracefully();

    }
    /**
     * @Author lsc
     * <p>通道初始化 </p>
     * @Param
     * @Return
     */
    private class ChildChannelHandler extends ChannelInitializer<SocketChannel> {

        @Override
        protected void initChannel(SocketChannel socketChannel) throws Exception {
            // 管道（Pipeline）持有某个通道的全部处理器
            ChannelPipeline pipeline = socketChannel.pipeline();
            pipeline.addLast(new StringDecoder());
            pipeline.addLast(new StringEncoder());
            // 添加处理器
            pipeline.addLast(new NettyServerHandler());

        }
    }
}
