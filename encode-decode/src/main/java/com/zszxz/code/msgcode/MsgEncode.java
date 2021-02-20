package com.zszxz.code.msgcode;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

import java.nio.charset.Charset;

/**
 * @Author lsc
 * <p> </p>
 */
public class MsgEncode extends MessageToByteEncoder {
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Object o, ByteBuf byteBuf) throws Exception {
        String msg = (String) o;
        byte[] bytes = msg.getBytes(Charset.forName("UTF-8"));
        byteBuf.writeBytes(bytes);
    }
}
