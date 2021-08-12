package org.everteam.evermq.handler;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.everteam.evermq.model.Topic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@ChannelHandler.Sharable
@Component
public class SubscribeHandler extends SimpleChannelInboundHandler<Topic> {

    private static final Logger LOGGER = LoggerFactory.getLogger(SubscribeHandler.class);

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Topic topic) throws Exception {
        LOGGER.info("notify topic {} subscribers", topic.getName());
    }
}
