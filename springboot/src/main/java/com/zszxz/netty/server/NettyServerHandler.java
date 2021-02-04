package com.zszxz.netty.server;

import com.zszxz.netty.mapper.NettyMapper;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @Author lsc
 * <p> 处理类 </p>
 */
@Slf4j
@Component
@ChannelHandler.Sharable
public class NettyServerHandler extends ChannelHandlerAdapter {

    @Autowired
    NettyMapper mapper;



    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        System.out.println("-----handler-------");
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
        String responseData = "zszxz-66666";
        //  数据写入缓冲区
        ByteBuf resp = Unpooled.copiedBuffer(responseData.getBytes());

        // 写入数据响应
        ChannelFuture channelFuture = ctx.writeAndFlush(resp);
        if (mapper==null){
            System.out.println("can you believe that the mapper is null");
        }
        List<Map> user = mapper.getUser();
        System.out.println(user);
        // 回调处理
        channelFuture.addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture channelFuture) throws Exception {
                if (channelFuture.isSuccess()){
                    log.info("success");
                }else {
                    log.error("error");
                }
            }
        });

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
