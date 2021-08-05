package org.everteam.evermq;

import org.everteam.evermq.server.NettyServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class EverMqApplication implements CommandLineRunner {

	private final NettyServer nettyServer;

	@Autowired
	public EverMqApplication(NettyServer nettyServer) {
		this.nettyServer = nettyServer;
	}

	public static void main(String[] args) throws InterruptedException {
		SpringApplication.run(EverMqApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		this.nettyServer.start();
	}
}
