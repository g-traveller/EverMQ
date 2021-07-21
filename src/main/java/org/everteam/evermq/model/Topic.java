package org.everteam.evermq.model;

import lombok.Data;

@Data
public class Topic {
    private String name;
    private long maxConnection;
}
