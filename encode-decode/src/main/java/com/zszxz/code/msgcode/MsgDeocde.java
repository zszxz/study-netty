package com.zszxz.code.msgcode;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * @Author lsc
 * <p> </p>
 */
public class MsgDeocde extends ByteToMessageDecoder {
    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {

        int size = byteBuf.readableBytes();
        if (size<=2){
            System.out.println("报文长度不满足抛弃");
            return;
        }
        // 创建字节数组
        byte[] bytes = new byte[size];
        // 缓冲区数据读入字节数组
        byteBuf.readBytes(bytes);
        // 编码转为字符串
        String body = (new String(bytes, "UTF-8"));
        list.add(body);
    }
}
