package com.zszxz.code.service;

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
        ctx.writeAndFlush(responseData);
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
