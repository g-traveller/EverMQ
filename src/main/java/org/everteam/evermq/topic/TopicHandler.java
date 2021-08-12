package org.everteam.evermq.topic;

import org.everteam.evermq.config.QueueConfig;
import org.everteam.evermq.exception.QueueOperationException;
import org.everteam.evermq.exception.TopicOperationException;
import org.everteam.evermq.model.Topic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class TopicHandler {

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

    public void pushMessage(String topicName, byte[] message) throws TopicOperationException {
        this.ensureTopicExists(topicName);
        Topic topic = this.getTopic(topicName);
        try {
            // check queue length
            if (topic.getEverQueue().getLength() >= queueConfig.getMaxConnection()) {
                throw new TopicOperationException(
                        String.format("Topic %s already exceed max length (%s)",
                                topicName,
                                queueConfig.getMaxConnection())
                );
            }
            topic.getEverQueue().enqueueNonBlock(message);
        } catch (QueueOperationException e) {
            throw new TopicOperationException(e.getMessage());
        }
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
}
