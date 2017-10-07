package com.unsan.netty.example.UnsanClient.Thread;

import java.util.Timer;
import java.util.TimerTask;

import com.unsan.msg.LaserLight;

import io.netty.channel.ChannelHandlerContext;
import io.netty.util.Timeout;

/**
 * 每3秒向客户端发送消息测试
 * 
 * @author gszhang
 *
 */

public class SendMsgPers extends   TimerTask{
	
	private  ChannelHandlerContext ctx;
	private final Timer timer = new Timer(true);
	private LaserLight msg;
	public SendMsgPers(ChannelHandlerContext ctx,LaserLight msg){
		this.msg = msg;
		this.ctx =  ctx;
	}
	public void start(){
		long delay = 5L;
		this.timer.schedule(this,delay, 3000l);
	}
	@Override
	public void run() {
		ctx.writeAndFlush(msg);
		
	}

} 
