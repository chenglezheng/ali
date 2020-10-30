package com.qww.clz.nio.netty.group;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.logging.LoggingHandler;

import java.util.Scanner;

/**
 * @Author chenglezheng
 * @Date 2020/8/21 9:31
 */
public class NettyClientGroup {

    private final int port;
    private final String host;

    public NettyClientGroup(int port, String host) {
        this.port = port;
        this.host = host;
    }

    public void run() throws Exception {
        EventLoopGroup workGroup = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(workGroup)
                    .channel(NioSocketChannel.class)
                    .handler(new LoggingHandler())
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel channel) throws Exception {
                            ChannelPipeline channelPipeline = channel.pipeline();
                            channelPipeline.addLast("decoder", new StringDecoder());
                            channelPipeline.addLast("encoder", new StringEncoder());
                            channelPipeline.addLast(new NettyClientHandler());
                        }
                    });
            ChannelFuture channelFuture = bootstrap.connect(this.host, this.port).sync();
            System.out.println("-----------------"+channelFuture.channel().remoteAddress()+"-----------------");
            Scanner scanner=new Scanner(System.in);
            while (scanner.hasNextLine()){
                String msg=scanner.nextLine();
                channelFuture.channel().writeAndFlush(msg);
            }
        } finally {
            workGroup.shutdownGracefully();
        }

    }

    public static void main(String[] args) throws Exception{
        new NettyClientGroup(9090, "localhost").run();
    }
}
