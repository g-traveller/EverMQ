package org.everteam.evermq.model;

import lombok.Data;
import org.everteam.evermq.queue.EverQueue;

@Data
public class Topic {
    private String name;
    private long maxConnection;
    private EverQueue<byte[]> everQueue;
}
