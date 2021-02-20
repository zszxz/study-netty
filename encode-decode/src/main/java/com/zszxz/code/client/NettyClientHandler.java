package com.zszxz.code.client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

/**
 * @Author lsc
 * <p> </p>
 */
@Slf4j
public class NettyClientHandler extends ChannelInboundHandlerAdapter {




    public NettyClientHandler() {
        super();
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

        String message = "关注公众号知识追寻者回复netty获取本教程源码";
        // 写入数据
        ctx.writeAndFlush(message);

    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        // 打印
        System.out.println("get the data from server: "+msg);
    }
}
