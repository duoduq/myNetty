package com.duoduq.helloNetty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**  

* Title: HelloServer  

* Description: 

* @author qwj  

* @date 2018年11月23日  

*/
public class HelloServer {
	private int port;
	
	public HelloServer(int port){
		this.port = port;
	}
	
	public void run() throws Exception{
		EventLoopGroup bossGroup = new NioEventLoopGroup();//用来接收进来的连接
		EventLoopGroup workGroup = new NioEventLoopGroup();//用来处理已经被接收的连接
		System.out.println("准备运行端口");
		try{
			ServerBootstrap b = new ServerBootstrap();
			b.group(bossGroup, workGroup).channel(NioServerSocketChannel.class)
			.childHandler(new ChannelInitializer<SocketChannel>() {

				@Override
				protected void initChannel(SocketChannel ch) throws Exception {
					 // 自定义处理类
					ch.pipeline().addLast(new HelloServerHandler());
					
				}
			}).option(ChannelOption.SO_BACKLOG, 128)
			.childOption(ChannelOption.SO_KEEPALIVE, true);
			//绑定端口，开始接收进来的拼接
			ChannelFuture f = b.bind(port).sync();
			//等待服务器socket关闭
			f.channel().closeFuture().sync();
		}catch(Exception e){
			workGroup.shutdownGracefully();
			bossGroup.shutdownGracefully();
		}
	}
	
	public static void main(String[] args) {
		try {
			new HelloServer(10110).run();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
