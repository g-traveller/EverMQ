package org.everteam.evermq.handler;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.everteam.evermq.config.QueueConfig;
import org.everteam.evermq.exception.QueueOperationException;
import org.everteam.evermq.exception.TopicOperationException;
import org.everteam.evermq.model.InMessage;
import org.everteam.evermq.model.Topic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@ChannelHandler.Sharable
@Component
public class TopicHandler extends SimpleChannelInboundHandler<InMessage> {

    private final QueueConfig queueConfig;
    private Map<String, Topic> topicMap;

    @Autowired
    public TopicHandler(QueueConfig queueConfig) {
        this.topicMap = new HashMap<>();
        this.queueConfig = queueConfig;
    }

    private Topic getTopic(String name) {
        return topicMap.get(name);
    }

    private void ensureTopicExists(String topicName) {
        // double check to avoid thread conflict
        if (this.getTopic(topicName) == null) {
            synchronized (TopicHandler.class) {
                if (this.getTopic(topicName) == null) {
                    topicMap.put(topicName, new Topic(topicName, queueConfig.getMaxConnection()));
                }
            }
        }
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, InMessage message) throws Exception {
        this.ensureTopicExists(message.getTopic());
        Topic topic = this.getTopic(message.getTopic());
        try {
            // check queue length
            if (topic.getEverQueue().getLength() >= queueConfig.getMaxConnection()) {
                throw new TopicOperationException(
                        String.format("Topic %s already exceed max length (%s)",
                                message.getTopic(),
                                queueConfig.getMaxConnection())
                );
            }
            topic.getEverQueue().enqueueNonBlock(message.getMessage());

            // fire next handler to notify all subscriber
            channelHandlerContext.fireChannelRead(topic);
        } catch (QueueOperationException e) {
            throw new TopicOperationException(e.getMessage());
        }
    }
}
