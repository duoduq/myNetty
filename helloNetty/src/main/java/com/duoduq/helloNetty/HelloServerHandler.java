package com.duoduq.helloNetty;

import java.nio.charset.Charset;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;
import io.netty.util.ReferenceCountUtil;

/**  

* Title: HelloServerHandler  

* Description: 输出接收到的消息

* @author qwj  

* @date 2018年11月23日  

*/
public class HelloServerHandler extends ChannelInboundHandlerAdapter {

	//收到数据是调用
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		try{
			ByteBuf in = (ByteBuf) msg;
			System.out.println(in.toString(CharsetUtil.UTF_8));
		}finally{
			ReferenceCountUtil.release(msg);
		}
		
		
	}

	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		cause.printStackTrace();
		ctx.close();
	}
	
}
