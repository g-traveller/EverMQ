package org.everteam.evermq.it;

import org.everteam.evermq.model.InMessage;
import org.junit.*;
import org.springframework.util.SerializationUtils;

import java.io.*;
import java.net.Socket;

public class SendMessageTest {
    private Socket clientSocket = null;
    private DataOutputStream out = null;
    private BufferedReader in = null;
    private static final String host = "127.0.0.1";
    private static final int port = 8080;

    @Before
    public void before() throws IOException {
        clientSocket = new Socket(host, port);
        clientSocket.setKeepAlive(true);
        out = new DataOutputStream(clientSocket.getOutputStream());
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
    }

    @After
    public void after() throws IOException {
        if (in != null) {
            in.close();
        }
        if (out != null) {
            out.close();
        }
        if (clientSocket != null) {
            clientSocket.close();
        }
    }

    @Test
    public void testSendByteMessage() throws Exception {
        for (int i = 0; i < 1; i++) {
            InMessage inMessage = new InMessage();
            inMessage.setTopic("test-topic");
            inMessage.setMessage("test-message".getBytes());

            byte[] byteMessage = SerializationUtils.serialize(inMessage);
            assert byteMessage != null;
            out.write(byteMessage);
            Thread.sleep(500);
        }
    }
}
