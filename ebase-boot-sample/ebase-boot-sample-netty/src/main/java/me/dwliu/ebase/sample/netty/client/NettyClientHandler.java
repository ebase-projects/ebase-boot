package me.dwliu.ebase.sample.netty.client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.EventLoop;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

@Slf4j
// @ChannelHandler.Sharable
public class NettyClientHandler extends ChannelInboundHandlerAdapter {
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		log.info("客户端 channelActive");
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		log.info("客户端收到消息: {}", msg.toString());
	}


	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		log.info("掉线了...");
		//使用过程中断线重连

		NettyClient nettyClient = new NettyClient();
		final EventLoop eventLoop = ctx.channel().eventLoop();
		eventLoop.schedule(nettyClient::start, 10L, TimeUnit.SECONDS);
		super.channelInactive(ctx);
	}


	/**
	 * 心跳检测
	 *
	 * @param ctx
	 * @param evt
	 * @throws Exception
	 */
	@Override
	public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
		if (evt instanceof IdleStateEvent) {
			IdleState state = ((IdleStateEvent) evt).state();
			if (IdleState.WRITER_IDLE == state) {
				ctx.writeAndFlush(Constant.HEARTBEAT);
			}

		} else {
			super.userEventTriggered(ctx, evt);
		}
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		cause.printStackTrace();
		ctx.close();
	}
}
