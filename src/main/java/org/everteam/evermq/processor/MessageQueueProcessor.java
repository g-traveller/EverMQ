package org.everteam.evermq.processor;

import org.everteam.evermq.model.InMessage;
import org.everteam.evermq.queue.EverQueue;
import org.everteam.evermq.util.SystemInformation;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

@Component
public class MessageQueueProcessor {

    private EverQueueProcessTask everQueueProcessTask;

    public MessageQueueProcessor(EverQueue<InMessage> everQueue) {
        everQueueProcessTask = new EverQueueProcessTask(everQueue);
    }

    @PostConstruct
    public void postConstruct() {
        // create thread pool with fixed size (core count * 2)
        int core = SystemInformation.getProcessorCoreCount();
        ThreadPoolExecutor executor =
                (ThreadPoolExecutor) Executors.newFixedThreadPool(core * 2);

        // commit task to process message in message queue.
        executor.submit(everQueueProcessTask);
    }

    @PreDestroy
    public void preDestroy() {
        this.everQueueProcessTask.setStopSign(true);
    }
}
