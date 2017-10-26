package com.unsan.netty.core.client.serverSelector;

import java.util.List;

public  interface IAddrSelector {

	public  String select(List<String> serverList);
}
