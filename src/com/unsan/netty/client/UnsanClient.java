package com.unsan.netty.client;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.unsan.msg.LaserLight;
import com.unsan.netty.core.client.NettyRobotClient;

import io.netty.channel.ChannelHandlerContext;

public class UnsanClient implements IClient{
	Logger log = LoggerFactory.getLogger(getClass());
	private static ConcurrentHashMap<String, ICallback> callbackMap = new ConcurrentHashMap<>();
	private  NettyRobotClient robotClient = null;
	private static CopyOnWriteArrayList< ChannelHandlerContext> ctxs = new CopyOnWriteArrayList<>();
	public UnsanClient() {

		robotClient = new NettyRobotClient();
		robotClient.start();
	}

	@Override
	public LaserLight aSend(LaserLight msg) {
		return null;
	}

	@Override
	public LaserLight aSend(LaserLight msg, ICallback callback) {
		callbackMap.put(msg.getNoid(), callback);
		if(robotClient.isConneted.get()){
			for(ChannelHandlerContext ctx : ctxs){
				ctx.writeAndFlush(msg);
				
			}
		}else{
			System.out.println("链接失败！！！");
		}
		return null;
	}
	
	
	public static ConcurrentHashMap<String, ICallback> getCallbackMap() {
		return callbackMap;
	}

	public static void setCallbackMap(ConcurrentHashMap<String, ICallback> callbackMap) {
		UnsanClient.callbackMap = callbackMap;
	}

	public static void addCtx(ChannelHandlerContext ctx){
		ctxs.add(ctx);
	}
	public static List<ChannelHandlerContext> getCtxs(){
		return ctxs;
	}
	
}
