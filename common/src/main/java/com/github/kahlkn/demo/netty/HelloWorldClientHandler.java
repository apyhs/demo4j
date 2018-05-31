package com.github.kahlkn.demo.netty;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class HelloWorldClientHandler extends ChannelInboundHandlerAdapter {
	private final String msg = "hello java world";
	 
	 
    /**
    * Creates a client-side handler.
    */
    public HelloWorldClientHandler() {
        //TODO
    }
 
 
    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        ctx.writeAndFlush(msg);
    }
 
 
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        System.out.println(msg);
        //        ctx.write(msg);
    }
 
 
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }
 
 
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        // Close the connection when an exception is raised.
        cause.printStackTrace();
        ctx.close();
    }
}
