package com.unsan.netty.core.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.unsan.msg.LaserLight;
import com.unsan.netty.client.ICallback;
import com.unsan.netty.client.UnsanClient;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

public class EchoClientHandler extends SimpleChannelInboundHandler<Object>{
	private Logger log = LoggerFactory.getLogger(getClass());
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		UnsanClient.addCtx(ctx);
	}
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
		LaserLight light = (LaserLight)msg;
		ICallback callback = UnsanClient.getCallbackMap().get(light.getNoid());
		if(callback!=null){
			callback.callback(light);
		}
		
	}
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		cause.printStackTrace();
		ctx.close();
	}

}
