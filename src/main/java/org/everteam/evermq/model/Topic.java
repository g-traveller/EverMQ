package org.everteam.evermq.model;

import lombok.Data;
import org.everteam.evermq.queue.EverQueue;

@Data
public class Topic {
    private String name;
    private int maxConnection;
    private EverQueue<byte[]> everQueue;

    public Topic(String name, int maxConnection) {
        this.name = name;
        this.maxConnection = maxConnection;
        this.everQueue = new EverQueue<>(maxConnection);
    }
}
