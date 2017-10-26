package com.unsan.netty.client;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Configure {
	private static final Logger log = LoggerFactory.getLogger(Configure.class);

	private static String confPath = "config" + File.separator + "nettyclient.properties";

	private static List<String> serverHosts = new ArrayList();
	private static int nettyServerPort;
	private static int timeOut = 60;

	private static int callbackTimeOut = 12;

	private static int channelPool = 10;

	private static boolean isSlave = false;

	public static void loadConf() {
		Properties properties = new Properties();
		InputStream inputStream = Configure.class.getClassLoader().getResourceAsStream(confPath);
		try {
			properties.load(inputStream);
			String hosts = properties.getProperty("nettyserver.hosts");
			if (hosts != null) {
				for (String host : hosts.split(",")) {
					serverHosts.add(host);
				}
			}
			nettyServerPort = Integer.parseInt(properties.getProperty("nettyserver.port"));
			timeOut = Integer.parseInt(properties.getProperty("nettyclient.timeout"));
			channelPool = Integer.parseInt(properties.getProperty("clientchannel.pool.size"));
			isSlave = Boolean.parseBoolean(properties.getProperty("nettyserver.slaveRandomSelector"));
			callbackTimeOut = Integer.parseInt(properties.getProperty("callback.timeout"));
			StringBuilder confInfo = new StringBuilder();
			confInfo.append("\n****************NETTYCLIENT CONF INFO******************").append("\nnettyserver.hosts\t")
					.append(serverHosts.toString()).append("\nnettyserver.port\t").append(nettyServerPort)
					.append("\nnettyclient.timeout\t").append(timeOut).append("\nclientchannel.pool.size\t")
					.append(channelPool).append("\nnettyserver.slaveRandomSelector\t").append(isSlave)
					.append("\ncallback.timeout\t").append(callbackTimeOut)
					.append("\n****************NETTYCLIENT CONF INFO******************");
			log.info(confInfo.toString());
		} catch (Exception e) {
			log.error("loki get config error", e);
		}
	}

	public static String getConfPath() {
		return confPath;
	}

	public static void setConfPath(String confPath) {
		Configure.confPath = confPath;
	}

	public static List<String> getServerHosts() {
		return serverHosts;
	}

	public static void setServerHosts(List<String> serverHosts) {
		Configure.serverHosts = serverHosts;
	}

	public static int getNettyServerPort() {
		return nettyServerPort;
	}

	public static void setNettyServerPort(int nettyServerPort) {
		Configure.nettyServerPort = nettyServerPort;
	}

	public static int getTimeOut() {
		return timeOut;
	}

	public static void setTimeOut(int timeOut) {
		Configure.timeOut = timeOut;
	}

	public static int getCallbackTimeOut() {
		return callbackTimeOut;
	}

	public static void setCallbackTimeOut(int callbackTimeOut) {
		Configure.callbackTimeOut = callbackTimeOut;
	}

	public static int getChannelPool() {
		return channelPool;
	}

	public static void setChannelPool(int channelPool) {
		Configure.channelPool = channelPool;
	}

	public static boolean isSlave() {
		return isSlave;
	}

	public static void setSlave(boolean isSlave) {
		Configure.isSlave = isSlave;
	}
	
	
	
}
