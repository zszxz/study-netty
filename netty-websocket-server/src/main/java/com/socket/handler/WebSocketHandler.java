package com.socket.handler;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketServerHandshaker;
import io.netty.handler.codec.http.websocketx.WebSocketServerHandshakerFactory;
import lombok.extern.slf4j.Slf4j;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author lsc
 * <p> </p>
 */
@Slf4j
public class WebSocketHandler extends SimpleChannelInboundHandler<Object>  {

    // 存放已经连接的通道
    private  static ConcurrentMap<String, Channel> ChannelMap=new ConcurrentHashMap();

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof FullHttpRequest){

            System.out.println("------------收到http消息--------------"+msg);
            handleHttpRequest(ctx,(FullHttpRequest)msg);
        }else if (msg instanceof WebSocketFrame){
            //处理websocket客户端的消息
            String message = ((TextWebSocketFrame) msg).text();
            System.out.println("------------收到消息--------------"+message);
//            ctx.channel().writeAndFlush(new TextWebSocketFrame(message));
            // 将消息回复给所有连接
            Collection<Channel> values = ChannelMap.values();
            for (Channel channel: values){
                channel.writeAndFlush(new TextWebSocketFrame(message));
            }
        }

    }

    /**
     * @author lsc
     * <p> 处理http请求升级</p>
     */
    private void handleHttpRequest(ChannelHandlerContext ctx,
                                   FullHttpRequest req) throws Exception {

        // 该请求是不是websocket upgrade请求
        if (isWebSocketUpgrade(req)) {
            String ws = "ws://127.0.0.1:8096";
            WebSocketServerHandshakerFactory factory = new WebSocketServerHandshakerFactory(ws, null, false);
            WebSocketServerHandshaker handshaker = factory.newHandshaker(req);

            if (handshaker == null) {// 请求头不合法, 导致handshaker没创建成功
                WebSocketServerHandshakerFactory.sendUnsupportedVersionResponse(ctx.channel());
            } else {
                // 响应该请求
                handshaker.handshake(ctx.channel(), req);
            }
            return;
        }
    }

    //n1.GET? 2.Upgrade头 包含websocket字符串?
    private boolean isWebSocketUpgrade(FullHttpRequest req) {
        HttpHeaders headers = req.headers();
        return req.method().equals(HttpMethod.GET)
                && headers.get(HttpHeaderNames.UPGRADE).equals("websocket");
    }


    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        //添加连接
        log.debug("客户端加入连接："+ctx.channel());
        Channel channel = ctx.channel();
        ChannelMap.put(channel.id().asShortText(),channel);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        //断开连接
        log.debug("客户端断开连接："+ctx.channel());
        Channel channel = ctx.channel();
        ChannelMap.remove(channel.id().asShortText());
    }
}
