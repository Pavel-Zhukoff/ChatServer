package ru.pavel_zhukoff.chat_server;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;

public class Connection {

    private static final int BUFFER_SIZE = 256;

    private SelectionKey selectionKey;
    private SocketChannel clientSocket;

    private ByteBuffer inputBuffer;

    public Connection(SelectionKey selectionKey) {
        this.selectionKey = selectionKey;
        this.clientSocket = (SocketChannel) selectionKey.channel();
        this.inputBuffer = ByteBuffer.allocate(BUFFER_SIZE);
    }

    public void send(String data) throws IOException {
        this.clientSocket.write(ByteBuffer.wrap(data.getBytes()));
    }

    public SelectionKey getSelectionKey() {
        return this.selectionKey;
    }

    public SocketChannel getClientSocket() {
        return this.clientSocket;
    }

    public ByteBuffer getInputBuffer() {
        return this.inputBuffer;
    }

    public int getBufferSize() {
        return BUFFER_SIZE;
    }
}
