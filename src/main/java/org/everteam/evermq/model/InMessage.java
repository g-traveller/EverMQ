package org.everteam.evermq.model;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

@Data
@ToString
public class InMessage implements Serializable {
    private static final long serialVersionUID = 979181933915242500L;
    private String topic;
    private byte[] message;
}
