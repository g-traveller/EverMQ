package org.everteam.evermq.model;

import org.everteam.evermq.exception.QueueOperationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ArrayBlockingQueue;

public class EverQueue {
    private static final Logger LOGGER = LoggerFactory.getLogger(EverQueue.class);

    private ArrayBlockingQueue<InMessage> messageArray;

    public EverQueue(int length) {
        messageArray = new ArrayBlockingQueue<>(length);
    }

    public void push(InMessage message) throws QueueOperationException {
        boolean result = this.messageArray.offer(message);
        if (!result) {
            throw new QueueOperationException("message is full");
        }
    }

    public InMessage poll() {
        return this.messageArray.poll();
    }
}
