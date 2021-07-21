package org.everteam.evermq.decoder;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import org.everteam.evermq.model.InMessage;
import org.springframework.util.SerializationUtils;

import java.util.List;

public class InMessageDecoder extends ByteToMessageDecoder {
    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext,
                          ByteBuf byteBuf,
                          List<Object> list) {
        // read message from buffer
        byte[] bytesMessage = new byte[byteBuf.readableBytes()];
        byteBuf.readBytes(bytesMessage);

        // deserialize buffer
        InMessage inMessage = (InMessage) SerializationUtils.deserialize(bytesMessage);
        list.add(inMessage);
    }
}
