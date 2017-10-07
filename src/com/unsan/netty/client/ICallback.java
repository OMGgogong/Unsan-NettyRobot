package com.unsan.netty.client;

import com.unsan.msg.LaserLight;

public interface ICallback {
	public abstract void callback(LaserLight msg);
}
