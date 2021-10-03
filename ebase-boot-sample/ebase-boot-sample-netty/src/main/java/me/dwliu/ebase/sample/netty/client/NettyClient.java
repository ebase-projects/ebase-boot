package me.dwliu.ebase.sample.netty.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

@Slf4j
public class NettyClient {

	private final String host = "ts.ubinavi.com.cn";
	private final Integer port = 6666;


	public void sendMsg(Channel channel, String msg) {
		channel.writeAndFlush(msg);

	}

	public void login(Channel channel) {
		sendMsg(channel, "i sdyt,sdyt0116\r\n");
	}

	public void logout(Channel channel) {
		sendMsg(channel, "o \r\n");
	}

	@SneakyThrows
	public void start() {
		EventLoopGroup group = new NioEventLoopGroup();
		Bootstrap bootstrap = new Bootstrap()
			.group(group)
			.option(ChannelOption.TCP_NODELAY, true)
			.option(ChannelOption.SO_KEEPALIVE, true)
			.channel(NioSocketChannel.class)
			.handler(new NettyClientInitializer());


		// try {
		ChannelFuture channelFuture = bootstrap.connect(host, port).sync();
		log.info("客户端成功");
		// channelFuture.channel().writeAndFlush("hello");
		// channelFuture.channel().closeFuture().sync();

		Channel channel = channelFuture.channel();

		channelFuture.addListener((ChannelFutureListener) future -> {
			if (future.isSuccess()) {
				log.info("连接Netty服务端成功");
				login(channel);
			} else {
				log.info("连接失败，进行断线重连");
				future.channel().eventLoop().schedule(this::start, 10L, TimeUnit.SECONDS);
			}
		});


	}

	public static void main(String[] args) {
		NettyClient nettyClient = new NettyClient();
		nettyClient.start();
	}


}
