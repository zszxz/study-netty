package com.zszxz.pro.service;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

/**
 * @Author lsc
 * <p>https://netty.io/wiki/reference-counted-objects.html </p>
 */
@Slf4j
public class NettyServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        // 转为字节缓冲区
        ByteBuf buf = (ByteBuf)msg;
        // 字节数组
        byte[] bytes = new byte[buf.readableBytes()];
        // 缓冲区数据读入字节数组
        buf.readBytes(bytes);
        // 编码转为字符串
        String body = (new String(bytes, "UTF-8"));
        System.out.println(" get the data from client : " + body);
        // 构造响应数据
        String responseData = "那天刚刚好遇见你";
        //  数据写入缓冲区
        ByteBuf resp = Unpooled.copiedBuffer(responseData.getBytes());
        // 写入数据响应
        ChannelFuture channelFuture = ctx.writeAndFlush(resp);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        // 写入 seocketChannel
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        // 异常关闭资源句柄
        ctx.close();
    }
}
