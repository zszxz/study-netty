package com.zszxz.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * @Author lsc
 * <p> </p>
 */
public class NettyClient {

    public static void main(String[] args) {
        NettyClient nettyClient = new NettyClient();
        // 连接的ip d端口
        nettyClient.connect(8080,"127.0.0.1");
    }

    public void connect(int port, String host) {

        // 创建线程组
        NioEventLoopGroup nioEventLoopGroup = new NioEventLoopGroup();
        // netty启动辅助类
        Bootstrap bootstrap = new Bootstrap();
        //
        bootstrap.group(nioEventLoopGroup)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.TCP_NODELAY, true)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        // 处理IO事件
                        ChannelPipeline pipeline = socketChannel.pipeline();
                        // 异常断线重连
                        pipeline.addLast(new NettyClientHandler(new NettyClient()));
                    }
                });
        // 异步操作
        ChannelFuture connect = null;
        try {
            connect = bootstrap
                    .connect(host, port)
                    .addListener(new ConnectionListener(this))// netty 启动时如果连接失败，会断线重连
                    .sync();
            // 关闭客户端
            connect.channel()
                    .closeFuture()
                    .sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            // 退出线程组
//            nioEventLoopGroup.shutdownGracefully();
            // netty 异常，服务端断线等情况下异常重连
//            final EventLoop eventLoop = connect.channel().eventLoop();
//            eventLoop.schedule(new Runnable() {
//                @Override
//                public void run() {
//                    System.out.println("666666666666666666");
//                    connect(8080, "127.0.0.1");
//                }
//            }, 5L, TimeUnit.SECONDS);
        }
    }
}
