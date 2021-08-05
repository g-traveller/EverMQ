package org.everteam.evermq.bean;

import org.everteam.evermq.config.QueueConfig;
import org.everteam.evermq.queue.EverQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QueueBean {

    private final QueueConfig queueConfig;

    @Autowired
    public QueueBean(QueueConfig queueConfig) {
        this.queueConfig = queueConfig;
    }

    @Bean
    public EverQueue everQueue() {
        return new EverQueue(this.queueConfig.getMaxConnection());
    }
}
