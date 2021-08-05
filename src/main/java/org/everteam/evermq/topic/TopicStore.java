package org.everteam.evermq.topic;

import org.everteam.evermq.exception.QueueOperationException;
import org.everteam.evermq.exception.TopicOperationException;
import org.everteam.evermq.model.Topic;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class TopicStore {

    private Map<String, Topic> topicMap;

    public TopicStore() {
        this.topicMap = new HashMap<>();
    }

    public Topic getTopic(String name) {
        return topicMap.get(name);
    }

    public void pushMessage(String topicName, byte[] message) throws TopicOperationException {
        Topic topic = this.getTopic(topicName);
        try {
            topic.getEverQueue().enqueueNonBlock(message);
        } catch (QueueOperationException e) {
            throw new TopicOperationException(e.getMessage());
        }
    }
}
