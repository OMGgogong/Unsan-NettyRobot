package com.unsan.netty.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.unsan.msg.LaserLight;

public abstract class UnsanCallBack implements ICallback {
	Logger log = LoggerFactory.getLogger(getClass());
	@Override
	public void callback(LaserLight msg) {
		log.debug("call success :"+msg.getMsg());
		
	}

}
