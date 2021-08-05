package org.everteam.evermq.handler;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.everteam.evermq.exception.TopicOperationException;
import org.everteam.evermq.model.InMessage;
import org.everteam.evermq.topic.TopicStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@ChannelHandler.Sharable
@Component
public class InMessageHandler extends SimpleChannelInboundHandler<InMessage> {

    private static final Logger LOGGER = LoggerFactory.getLogger(InMessageHandler.class);

    private final TopicStore topicStore;

    @Autowired
    public InMessageHandler(TopicStore topicStore) {
        this.topicStore = topicStore;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext,
                                InMessage inMessage) {
        String message = new String(inMessage.getMessage());
        LOGGER.info("get channel read: " + message);

        // save to message queue by topic
        try {
            this.topicStore.pushMessage(inMessage.getTopic(), inMessage.getMessage());
        } catch (TopicOperationException e) {
            e.printStackTrace();
            LOGGER.error("failed to insert message: {}", e.getLocalizedMessage());
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        LOGGER.error(cause.getLocalizedMessage());
        ctx.close();
    }
}
