package com.zszxz.service;

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
        System.out.println(" get the data from client : " + msg);
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
