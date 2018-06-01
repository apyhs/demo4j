package com.github.kahlkn.demo4j.function.server.socket.tcp.show.bio;

import com.github.kahlkn.artoria.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;

public class SocketHandleThread extends Thread {
    private Socket socket;

    public SocketHandleThread(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        InputStream in = null;
        try {
            in = socket.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            StringBuilder builder = new StringBuilder();
            builder.append(">>>>>>>>>>>>>>>>").append("\r\n");
            for (String line; StringUtils.isNotBlank((line = reader.readLine())); ) {
                builder.append(line).append("\r\n");
            }
            builder.append("<<<<<<<<<<<<<<<<").append("\r\n");
            System.out.println(builder);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(in);
            IOUtils.closeQuietly(socket);
        }
    }

}
