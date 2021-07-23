package org.everteam.evermq.processor;

import org.everteam.evermq.model.InMessage;
import org.everteam.evermq.queue.EverQueue;


public class EverQueueProcessTask implements Runnable {

    private EverQueue<InMessage> everQueue;
    private boolean stopSign = false;

    public EverQueueProcessTask(EverQueue<InMessage> everQueue) {
        this.everQueue = everQueue;
    }

    @Override
    public void run() {
        // check the message from queue
        while (!stopSign) {
            InMessage message = this.everQueue.dequeueBlock();
            if (message != null) {
                // push message to client socket

            }
        }
    }

    public void setStopSign(boolean stopSign) {
        this.stopSign = stopSign;
    }
}
