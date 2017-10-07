package com.unsan.netty.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.unsan.msg.LaserLight;

public class UnsanCallBack implements ICallback {
	Logger log = LoggerFactory.getLogger(getClass());
	@Override
	public void callback(LaserLight msg) {
		log.debug("UnsanCallBack:"+msg.getMsg());
		System.out.println("UnsanCallBack:"+msg.getMsg());
		
	}

}
