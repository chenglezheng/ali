package com.qww.clz.nio.netty;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.text.SimpleDateFormat;
import java.util.concurrent.TimeUnit;

/**
 * @Author chenglezheng
 * @Date 2020/7/31 16:27
 */
public class NettyServerHandler extends SimpleChannelInboundHandler<String> {

    //GlobalEventExecutor.INSTANCE) 是全局的事件执行器，是一个单例
    private static ChannelGroup channelGroup=new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    //handlerAdded 表示连接建立，一点连接，第一个被执行
    //将当前channel 加入到 channelGroup
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        Channel channel=ctx.channel();
        //将该客户端聊天的信息推送给其它在线的客户端
        /**
         *
         * 该方法会将channelGroup 中的所有channel遍历，并发送消息
         * 我们不需要自己遍历
         */
        channelGroup.writeAndFlush("[客户端]"+channel.remoteAddress()+"加入聊天"+ sf.format(new java.util.Date()) + " \n");
        channelGroup.add(channel);
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        Channel channel=ctx.channel();
        channelGroup.writeAndFlush("[客户端]" + channel.remoteAddress() + " 离开了\n");
        System.out.println("channelGroup size" + channelGroup.size());
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(ctx.channel().remoteAddress() + " 上线了~");
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(ctx.channel().remoteAddress() + " 离线了~");
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, String s) throws Exception {
        //获取到当前channel
        Channel channel=channelHandlerContext.channel();
        channel.eventLoop().execute(()->{
            try{
                Thread.sleep(5000);
                channelGroup.forEach(channel1 -> {
                    if(channel1!=channel){
                        channel1.writeAndFlush("[客户]" + channel.remoteAddress() + " 发送了数据\n" + s + "\n");
                    }else{
                        //回显自己发送的消息给自己
                        channel.writeAndFlush("[自己]发送了数据" + s + "\n");
                    }
                });
            }catch (Exception e){
                e.printStackTrace();
            }
        });
        channel.eventLoop().schedule(()->{
            try{
                Thread.sleep(5000);
                channelGroup.forEach(channel1 -> {
                    if(channel1!=channel){
                        channel1.writeAndFlush("[客户]" + channel.remoteAddress() + " 发送了调度数据\n" + s + "\n");
                    }else{
                        //回显自己发送的消息给自己
                        channel.writeAndFlush("[自己]发送了数据" + s + "\n");
                    }
                });
            }catch (Exception e){
                e.printStackTrace();
            }
        },5000,TimeUnit.MILLISECONDS);
        channelGroup.forEach(channel1 -> {
            if(channel1!=channel){
                channel1.writeAndFlush("[客户]" + channel.remoteAddress() + " 发送了消息\n" + s + "\n");
            }else{
                //回显自己发送的消息给自己
                channel.writeAndFlush("[自己]发送了消息" + s + "\n");
            }
        });

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
