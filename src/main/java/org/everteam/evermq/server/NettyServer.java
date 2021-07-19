package org.everteam.evermq.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import org.everteam.evermq.config.NettyConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;
import javax.annotation.Resource;

@Component
public class NettyServer {

    private static final Logger LOGGER = LoggerFactory.getLogger(NettyServer.class);

    private EventLoopGroup bossGroup;
    private EventLoopGroup workerGroup;

    @Resource
    private NettyConfig nettyConfig;

    public void start() throws InterruptedException {
        this.bossGroup = new NioEventLoopGroup(1);
        this.workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 100)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline p = ch.pipeline();
                        }
                    });

            ChannelFuture f = b.bind(nettyConfig.getPort()).sync();
            LOGGER.info("start netty server successfully");
            f.channel().closeFuture().sync();

        } finally {
            // Shut down all event loops to terminate all threads.
            LOGGER.info("shutdown netty server");
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    @PreDestroy
    public void close() {
        LOGGER.info("shutdown netty server");
        this.bossGroup.shutdownGracefully();
        this.workerGroup.shutdownGracefully();
    }
}
