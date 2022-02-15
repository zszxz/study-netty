package com.socket.handler;

import com.socket.client.WebSocketClient;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.WebSocketClientHandshaker;
import lombok.extern.slf4j.Slf4j;

/**
 * @author lsc
 * <p> </p>
 */
@Slf4j
public class ClientHandler extends SimpleChannelInboundHandler<Object> {
    private WebSocketClientHandshaker webSocketClientHandshaker;
    private WebSocketClient webSocketClient;

    public ClientHandler(WebSocketClientHandshaker webSocketClientHandshaker
            ,WebSocketClient webSocketClient) {
        this.webSocketClientHandshaker = webSocketClientHandshaker;
        this.webSocketClient = webSocketClient;
    }

    @Override
    public void channelActive(ChannelHandlerContext channelHandlerContext) {
        Channel channel = channelHandlerContext.channel();
        // 握手
        webSocketClientHandshaker.handshake(channel);
        log.info("-----------握手-----------");
    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        log.error("------异常----------{}",cause.getMessage());
        ctx.close();

    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) {

        log.info("---------服务器端断开连接---------");
    }


    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Object msg) throws Exception {
        System.out.println("-----收到消息------"+msg);


    }


}