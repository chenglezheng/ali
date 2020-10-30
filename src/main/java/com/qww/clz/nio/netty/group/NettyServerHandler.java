package com.qww.clz.nio.netty.group;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

/**
 * @Author chenglezheng
 * @Date 2020/8/20 18:49
 */
public class NettyServerHandler extends SimpleChannelInboundHandler<String> {

    //定义一个channle 组，管理所有的channel
    //GlobalEventExecutor.INSTANCE) 是全局的事件执行器，是一个单例
    private static ChannelGroup channels=new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);


    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(ctx.channel().remoteAddress()+"上线了!");
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(ctx.channel().remoteAddress()+"离线了!");
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        channels.writeAndFlush(ctx.channel().remoteAddress()+"加入了群聊!");
        channels.add(ctx.channel());
        System.out.println("当前群聊人数："+channels.size()+"人！");
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        channels.writeAndFlush(ctx.channel().remoteAddress()+"离开了群聊!");
        System.out.println("当前群聊人数："+channels.size()+"人！");
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, String s) throws Exception {
        channels.forEach((ch)->{
            if(ch!=channelHandlerContext.channel()){
                ch.writeAndFlush(channelHandlerContext.channel().remoteAddress()+"说:"+s);
            }else{
                ch.writeAndFlush("自己发的消息："+s);
            }
        });
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.channel().close();
    }
}
