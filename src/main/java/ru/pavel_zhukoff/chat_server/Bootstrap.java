package ru.pavel_zhukoff.chat_server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

public class Bootstrap {

    private static final int BUFFER_SIZE = 256;

    private static Bootstrap instance = null;
    private ServerSocketChannel serverChannel;
    private Selector selector;
    private Map<String, SocketChannel> connectedClients;

    private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    private Bootstrap() {
        try {
            this.selector = Selector.open();
            this.serverChannel = ServerSocketChannel.open();
            this.serverChannel.configureBlocking(false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Bootstrap getInstance() {
        if (Bootstrap.instance == null) {
            return new Bootstrap();
        }
        return Bootstrap.instance;
    }

    public void run() {
        this.run("0.0.0.0", 8000);
    }

    public void run(int port) {
        this.run("0.0.0.0", port);
    }

    public void run(String host, int port) {
        try {
            this.serverChannel.bind(new InetSocketAddress(host, port));
            this.serverChannel.register(this.selector, SelectionKey.OP_ACCEPT);
            LOGGER.info(String.format("Server started on %s:%d", host, port));
            while (true) {
                this.selector.select();
                Set<SelectionKey> selectedKeys = this.selector.selectedKeys();
                Iterator<SelectionKey> iter = selectedKeys.iterator();
                while (iter.hasNext()) {
                    SelectionKey key = iter.next();
                    if (key.isAcceptable()) {
                        SocketChannel client = this.serverChannel.accept();
                        client.configureBlocking(false);
                        client.register(this.selector, SelectionKey.OP_READ | SelectionKey.OP_WRITE);
                        LOGGER.info(String.format("Client %s connected", client.getRemoteAddress().toString()));
                    }
                    if (key.isReadable()) {
                        this.handleClient(new Connection(key));
                    }
                    iter.remove();
                }

            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void handleClient(Connection connection) throws IOException {
        SocketChannel clientChannel = connection.getClientSocket();
        while (clientChannel != null && clientChannel.isOpen()) {
            ByteBuffer buffer = connection.getInputBuffer();
            StringBuilder builder = new StringBuilder();
            boolean keepReading = true;

            while (keepReading) {
                clientChannel.read(buffer);

                int position = buffer.position();
                keepReading = position == connection.getBufferSize();

                byte[] array = keepReading
                        ? buffer.array()
                        : Arrays.copyOfRange(buffer.array(), 0, position);

                builder.append(new String(array));
                buffer.clear();
            }

            buffer.flip();
            connection.send(String.format("Requested data = %s", builder.toString())); // Write to client

            clientChannel.close();
            LOGGER.info(String.format("Client's request:\n %s", builder.toString()));
        }
    }

    public void stop() throws IOException {
        this.serverChannel.close();
    }
}
