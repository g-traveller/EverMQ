package org.everteam.evermq.handler;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.everteam.evermq.model.InMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ChannelHandler.Sharable
public class ServerHandler extends SimpleChannelInboundHandler<InMessage> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ServerHandler.class);

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, InMessage inMessage) {
        String message = new String(inMessage.getMessage());
        LOGGER.info("get channel read: " + message);
    }
}
