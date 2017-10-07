package com.unsan.netty.example.connectionClient;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.unsan.netty.core.client.NettyRobotClient;

/**
 * 【】测试链接数的代码
 * 目前测试 服务器可链接  7*1024 连接数 暂无失败链接！
 * --------------------------------------------------
 * 服务器CPU信息 【命令 lscpu】：
 * Architecture:          x86_64
 * CPU op-mode(s):        32-bit, 64-bit
 * Byte Order:            Little Endian
 * CPU(s):                8
 * On-line CPU(s) list:   0-7
 * Thread(s) per core:    1
 * Core(s) per socket:    1
 * CPU socket(s):         8
 * NUMA node(s):          1
 * Vendor ID:             GenuineIntel
 * CPU family:            6
 * Model:                 62
 * Stepping:              7
 * CPU MHz:               1995.192
 * BogoMIPS:              3990.38
 * L1d cache:             32K
 * L1i cache:             32K
 * L2 cache:              256K
 * L3 cache:              16384K
 * NUMA node0 CPU(s):     0-7
 * ----------------------------------------------------
 * 服务器 内存 【命令 free -m】
 *              total       used       free     shared    buffers     cached
 * Mem:          7873       1997       5876          0        389        877
 * -/+ buffers/cache:        729       7143
 * Swap:        16383          0      16383
 * 
 * 
 * @author gszhang
 *
 */
public class ConcurrencyTest {
public static void main(String[] args) {
	 ExecutorService cachep = Executors.newFixedThreadPool(1024);
	 for(int i = 0 ;i<1024;i++){
		 cachep.execute(new NettyRobotClient());
	 }
}
}
