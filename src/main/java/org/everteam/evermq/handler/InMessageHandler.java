package org.everteam.evermq.handler;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.everteam.evermq.exception.QueueOperationException;
import org.everteam.evermq.queue.EverQueue;
import org.everteam.evermq.model.InMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@ChannelHandler.Sharable
@Component
public class InMessageHandler extends SimpleChannelInboundHandler<InMessage> {

    private static final Logger LOGGER = LoggerFactory.getLogger(InMessageHandler.class);

    @Autowired
    private EverQueue<InMessage> everQueue;

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, InMessage inMessage)
            throws QueueOperationException {
        String message = new String(inMessage.getMessage());
        // save to message queue
        everQueue.enqueueNonBlock(inMessage);

        LOGGER.info("get channel read: " + message);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        LOGGER.error(cause.getLocalizedMessage());
        ctx.close();
    }
}
