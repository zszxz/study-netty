package com.zszxz.client;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.EventLoop;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * @Author lsc
 * <p> </p>
 */
@Slf4j
public class NettyClientHandler extends ChannelInboundHandlerAdapter {

    private NettyClient nettyClient;


    public NettyClientHandler(NettyClient nettyClient) {
        this.nettyClient = nettyClient;
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.warn("Unexpected exception from downstream : [{}]" ,cause.getMessage());
    }

    /* *
     * @Author lsc
     * <p>触发回调 </p>
     * @Param [ctx]
     * @Return void
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        byte[] bytes = "关注公众号知识追寻者回复netty获取本教程源码%".getBytes();
        // 创建节字缓冲区
        ByteBuf message = Unpooled.buffer(bytes.length);
        // 将数据写入缓冲区
        message.writeBytes(bytes);
        // 写入数据
        ctx.writeAndFlush(message);

    }

    /**
     * @Author lsc
     * <p> 运行时断线重连</p>
     * @Param [ctx]
     * @Return
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        final EventLoop eventLoop = ctx.channel().eventLoop();
        eventLoop.schedule(new Runnable() {
            @Override
            public void run() {
                System.out.println("22222222222222222");
                nettyClient.connect(8080, "127.0.0.1");
            }
        }, 1L, TimeUnit.SECONDS);
        super.channelInactive(ctx);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        // 消息转为 字节缓冲区
        ByteBuf buf = (ByteBuf)msg;
        // 创建字节数组
        byte[] bytes = new byte[buf.readableBytes()];
        // 获得响应的数据写入字节数组
        buf.readBytes(bytes);
        // 字节数组转为字符串
        String body = new String(bytes, "UTF-8");
        // 打印
        System.out.println("get the data from server: "+body);
    }
}
