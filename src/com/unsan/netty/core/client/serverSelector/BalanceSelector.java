package com.unsan.netty.core.client.serverSelector;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BalanceSelector implements IAddrSelector{
	private Logger log = LoggerFactory.getLogger(getClass());
	private static AtomicInteger counter = new AtomicInteger(0);
	private static final String DEFAULT_SERVER = "127.0.0.1";
	@Override
	public  String select(List<String> serverList) {
		if(serverList==null||(serverList!=null&&serverList.size()==0)){
			return DEFAULT_SERVER;
		}
		int remander = counter.getAndIncrement()%serverList.size();
		log.info("netty服务："+serverList.get(remander));
		return serverList.get(remander);
	}

}
