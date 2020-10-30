package com.qww.clz.nio.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.util.Scanner;

/**
 * @Author chenglezheng
 * @Date 2020/8/3 14:19
 */
public class NettyClient {

    //属性
    private final String host;
    private final int port;

    public NettyClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void run() throws Exception{

        EventLoopGroup group=new NioEventLoopGroup();


        try {
            Bootstrap bootstrap=new Bootstrap();
            bootstrap.group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            //得到pipeline
                            ChannelPipeline pipeline=socketChannel.pipeline();
                            //向pipeline加入解码器
                            pipeline.addLast("decoder", new StringDecoder());
                            //向pipeline加入编码器
                            pipeline.addLast("encoder", new StringEncoder());
                            pipeline.addLast(new NettyClientHandler());


                        }
                    });
            ChannelFuture channelFuture=bootstrap.connect(host,port).sync();
            //得到Channel
            Channel channel=channelFuture.channel();
            System.out.println("------------------"+channel.localAddress()+"--------------------");
            //客户端需要输入信息，创建一个扫描器
            Scanner scanner=new Scanner(System.in);
            while (scanner.hasNextLine()){
                String msg=scanner.nextLine();
                //通过channel发送到服务器
                channel.writeAndFlush(msg);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            group.shutdownGracefully();
        }


    }


    public static void main(String[] args) throws Exception{
        NettyClient nettyClient=new NettyClient("localhost",9898);
        nettyClient.run();
    }


}
