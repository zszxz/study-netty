package com.zszxz.code.client;

import com.zszxz.code.msgcode.MsgDeocde;
import com.zszxz.code.msgcode.MsgEncode;
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

    public static void main(String[] args) throws Exception {
        NettyClient nettyClient = new NettyClient();
        // 连接的ip d端口
        nettyClient.connect(8080,"127.0.0.1");
    }

    public void connect(int port, String host) throws InterruptedException {

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
                        // 添加编码器和解码器
                        pipeline.addLast(new MsgEncode());
                        pipeline.addLast(new MsgDeocde());
                        // 处理器
                        pipeline.addLast(new NettyClientHandler());
                    }
                });
        // 异步操作
        ChannelFuture connect = bootstrap.connect(host, port).sync();
        // 关闭客户端
        connect.channel().closeFuture().sync();
        // 退出线程组
        nioEventLoopGroup.shutdownGracefully();
    }
}
