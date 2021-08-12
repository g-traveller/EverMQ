package org.everteam.evermq.queue;

import org.everteam.evermq.exception.QueueOperationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ArrayBlockingQueue;

public class EverQueue<T> {
    private static final Logger LOGGER = LoggerFactory.getLogger(EverQueue.class);

    private ArrayBlockingQueue<T> messageArray;

    public EverQueue(int length) {
        messageArray = new ArrayBlockingQueue<>(length);
    }

    public void enqueueNonBlock(T message) throws QueueOperationException {
        boolean result = this.messageArray.offer(message);
        if (!result) {
            LOGGER.error("ever queue is full when pushing");
            throw new QueueOperationException("ever queue is full");
        }
    }

    public T dequeueBlock() {
        T inMessage = null;
        try {
            // no need to lock because it is thread safe.
            inMessage = this.messageArray.take();
        } catch (InterruptedException e) {
            LOGGER.info("ever queue is interrupted");
        }
        return inMessage;
    }

    public long getLength() {
        return this.messageArray.size();
    }
}
