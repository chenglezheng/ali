package com.qww.clz.nio.netty.group;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

/**
 * @Author chenglezheng
 * @Date 2020/8/20 18:39
 */
public class NettyServerGroup {

    private int port;


    public NettyServerGroup(int port) {
        this.port = port;
    }

    public void startServer() throws Exception {
        EventLoopGroup boosGroup = new NioEventLoopGroup(2);
        EventLoopGroup workGroup = new NioEventLoopGroup(3);
        try {
            ServerBootstrap bootstrap = new ServerBootstrap();

            bootstrap.group(boosGroup, workGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            ChannelPipeline pipeline = socketChannel.pipeline();
                            pipeline.addLast("decoder", new StringDecoder());
                            pipeline.addLast("encoder", new StringEncoder());
                            pipeline.addLast("serverHandle", new NettyServerHandler());
                        }
                    });
            ChannelFuture channelFuture = bootstrap.bind(this.port).sync();
            channelFuture.addListener(ch->{
               if(channelFuture.isSuccess()){
                   System.out.println("群聊系统服务器启动成功,监听的端口号为："+this.port);
               }else{
                   System.out.println("群聊系统服务器启动失败！");
               }
            });
            channelFuture.channel().closeFuture().sync();
        } finally {
            boosGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }
    }


    public static void main(String[] args) throws Exception{
        NettyServerGroup nettyServerGroup=new NettyServerGroup(9090);
        nettyServerGroup.startServer();
    }

}
