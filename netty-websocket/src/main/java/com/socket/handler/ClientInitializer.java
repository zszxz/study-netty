package com.socket.handler;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.nio.charset.Charset;

/**
 * @author lsc
 * <p> </p>
 */
public class ClientInitializer extends ChannelInitializer<SocketChannel> {

    private ClientHandler clientHandler;

    public ClientInitializer(ClientHandler clientHandler) {
        this.clientHandler = clientHandler;
    }

    @Override
    protected void initChannel(SocketChannel channel)  {
        ChannelPipeline pipeline = channel.pipeline();
        pipeline.addLast(new HttpClientCodec());
        pipeline.addLast("encoder", new StringEncoder(Charset.forName("UTF-8")));
        pipeline.addLast("decoder", new StringDecoder(Charset.forName("UTF-8")));

        pipeline.addLast("handler",clientHandler);

    }
}
