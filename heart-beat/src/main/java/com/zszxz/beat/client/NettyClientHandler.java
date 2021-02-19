package com.zszxz.beat.client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleStateEvent;
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
        ctx.writeAndFlush(message);

    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        // 打印
        System.out.println("get the data from server: "+msg);
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent e = (IdleStateEvent) evt;
            switch (e.state()) {
                case READER_IDLE:
                    System.out.println("读空闲");
                    break;
                case WRITER_IDLE:
                    // 写空闲，发送心跳
                    System.out.println("写空闲，发送心跳包");
                    ctx.writeAndFlush("1");
                    break;
                case ALL_IDLE:
                    System.out.println("读写空闲");
                    break;
                default:
                    break;
            }
        }
        super.userEventTriggered(ctx, evt);
    }
}
