package com.qww.clz.nio.netty.http;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @Author chenglezheng
 * @Date 2020/8/11 14:46
 */
public class HttpServer {

    public static void main(String[] args) {
        EventLoopGroup httpServerGroup = new NioEventLoopGroup();
        EventLoopGroup workGroup = new NioEventLoopGroup();
        try{
            ServerBootstrap serverBootstrap=new ServerBootstrap();
                serverBootstrap.group(httpServerGroup,workGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new HttpServerInit());
            ChannelFuture channelFuture = serverBootstrap.bind(8083).sync();
            channelFuture.addListener((ch)->{
                if(ch.isSuccess()){
                    System.out.println("端口监听成功！");
                }else{
                    System.out.println("端口监听失败！");
                }
            });
            channelFuture.channel().closeFuture().sync();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            httpServerGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }
    }

}
