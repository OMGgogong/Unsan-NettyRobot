package com.unsan.netty.example.UnsanClient;

import com.unsan.msg.LaserLight;
import com.unsan.netty.client.UnsanCallBack;
import com.unsan.netty.client.UnsanClient;

/**
 * 【】启动之前 请先启动服务
 * 
 * 从客户端 通过netty把消息交给nettyRobotClient  然后有 nettyRobotClient 传给 nettyRobot服务器 最后交给 Unsan里面的wali机器人
 * @author gszhang
 *
 */
public class UnsanClientTest {
public static void main(String[] args) {
	UnsanClient unsanClient = new UnsanClient();
	LaserLight sendMsg = new LaserLight();
	sendMsg.setFromRobotName("netty");
	sendMsg.setToRobotName("wali");
	sendMsg.setMsg("this is a frist msg that is came from nettyRobot to wali !");
	
	unsanClient.aSend(sendMsg, new UnsanCallBack());
}
}
