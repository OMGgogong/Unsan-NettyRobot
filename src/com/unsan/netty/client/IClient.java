package com.unsan.netty.client;

import com.unsan.msg.LaserLight;

public interface IClient {
public abstract LaserLight aSend(LaserLight msg);
/**
 * 异步发送
 * @param msg
 * @param callback
 * @return
 */
public abstract LaserLight aSend(LaserLight msg,ICallback callback);
}
