package com.unsan.netty.core.client;

import java.net.InetSocketAddress;
import java.util.concurrent.atomic.AtomicBoolean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.unsan.netty.client.Configure;
import com.unsan.netty.core.client.serverSelector.BalanceSelector;

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
	private Logger log = LoggerFactory.getLogger(getClass());
	private final String host = new BalanceSelector().select(Configure.getServerHosts());
	private final int port = Configure.getNettyServerPort();
	
	/**
	 * 多线程下 线程安全
	 * 该客户端的链接状态
	 */
	private   AtomicBoolean isConneted = new AtomicBoolean(false);
	
	/**
	 * 此方法用到了 CAS 自旋锁
	 * 自旋锁 是不可重入的!即：不可回调。
	 * 
	 */
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
						log.info("客户端链接成功！");
						
						while(!isConneted.get()){
							isConneted.compareAndSet(false, true);
							
						}
						
						
					}else{
						log.error("客户端链接失败！");
						while(isConneted.get()){
							isConneted.compareAndSet(true, false);
							
						}
						future.cause().printStackTrace();
					}
				}
			});
			//f.channel().closeFuture().sync();
		} catch (InterruptedException e) {
			log.error("客户端链接异常！");
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


	/**
	 * 该客户端的链接状态
	 * true表明客户端链接成功
	 * false链接失败
	 * @return
	 */
	public AtomicBoolean getIsConneted() {
		return isConneted;
	}


	public void setIsConneted(AtomicBoolean isConneted) {
		this.isConneted = isConneted;
	}

}
