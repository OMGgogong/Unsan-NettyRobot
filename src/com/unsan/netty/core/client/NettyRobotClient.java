package com.unsan.netty.core.client;

import java.net.InetSocketAddress;
import java.util.concurrent.atomic.AtomicBoolean;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;

/**
 * 采用 java序列化 编码 解码
 * @author gszhang
 *
 */
public class NettyRobotClient implements Runnable{

	private final String host = "127.0.0.1";
	private final int port = 6666;
	
	public   AtomicBoolean isConneted = new AtomicBoolean(false);
	

	public void start() {
		
		EventLoopGroup group = new NioEventLoopGroup();
		 
		Bootstrap b = new Bootstrap();
		b.group(group);
		b.channel(NioSocketChannel.class).option(ChannelOption.TCP_NODELAY, true);
		b.remoteAddress(new InetSocketAddress(host,port));
		b.handler(new ChannelInitializer<SocketChannel>() {

			@Override
			protected void initChannel(SocketChannel ch) throws Exception {
				//ch.pipeline().addLast()
				ch.pipeline().addLast("decoder",new ObjectDecoder(104857600, ClassResolvers.weakCachingConcurrentResolver(null)));
				ch.pipeline().addLast("encoder", new ObjectEncoder());
				ch.pipeline().addLast(new EchoClientHandler());
			}
		});
		
		try {
			ChannelFuture f  = b.connect(host,port).sync();
			f.addListener(new ChannelFutureListener() {
				
				@Override
				public void operationComplete(ChannelFuture future) throws Exception {
					if(future.isSuccess()){
						System.out.println("client conneted");
						
						
						while(!isConneted.get()){
							isConneted.compareAndSet(false, true);
							
						}
						
						
					}else{
						System.out.println("server attemp failed");
						
						while(isConneted.get()){
							isConneted.compareAndSet(true, false);
							
						}
						future.cause().printStackTrace();
					}
				}
			});
			//f.channel().closeFuture().sync();
		} catch (InterruptedException e) {
			System.out.println("异常退出");
			e.printStackTrace();
		}
//		finally {
//			try {
//				System.out.println("退出");
//				group.shutdownGracefully().sync();
//			} catch (InterruptedException e) {
//				System.out.println("退出失败");
//				e.printStackTrace();
//			}
//		}
		
	}
	
	
	public static void main(String[] args) {
			
		new NettyRobotClient().start();
	}


	@Override
	public void run() {
		new NettyRobotClient().start();
	}

}
