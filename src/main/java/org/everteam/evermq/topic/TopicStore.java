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

    private Topic getTopic(String name) {
        return topicMap.get(name);
    }

    public void pushMessage(String topicName, byte[] message) throws TopicOperationException {
        this.ensureTopicExists(topicName);
        Topic topic = this.getTopic(topicName);
        try {
            topic.getEverQueue().enqueueNonBlock(message);
        } catch (QueueOperationException e) {
            throw new TopicOperationException(e.getMessage());
        }
    }

    private void ensureTopicExists(String topicName) {
        // double check to avoid thread conflict
        if (this.getTopic(topicName) == null) {
            synchronized (TopicStore.class) {
                if (this.getTopic(topicName) == null) {
                    topicMap.put(topicName, new Topic());
                }
            }
        }
    }
}
