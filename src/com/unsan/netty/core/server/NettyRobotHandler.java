package com.unsan.netty.core.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.unsan.msg.LaserLight;
import com.unsan.netty.example.UnsanClient.Thread.SendMsgPers;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class NettyRobotHandler extends ChannelInboundHandlerAdapter {
	private Logger log = LoggerFactory.getLogger(getClass());
	private SendMsgPers sendp = null;
	
	private NettyRobot nettyRobot = null;
	
	
	public NettyRobotHandler(NettyRobot nettyRobot) {
		this.nettyRobot = nettyRobot;
	}
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		LaserLight light = (LaserLight)msg;
		System.out.println(light.getNoid()+" netty 内部服务器 消息 "+light.getLevel()+"msg:"+light.getMsg());
		
		sendp = new SendMsgPers(ctx,light);
		sendp.start();
		
		light.getToRobotName();
		
		LaserLight ceshi = new LaserLight();
		 ceshi.setMsg("服务器会每隔3秒发送消息--调用callback！");
		 //数据发送出去...
		 nettyRobot.aSend(light);
				 
		ctx.writeAndFlush(ceshi);
	}

	
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		// TODO Auto-generated method stub
		//super.exceptionCaught(ctx, cause);
		cause.printStackTrace();
		
		ctx.close();
	}
}
