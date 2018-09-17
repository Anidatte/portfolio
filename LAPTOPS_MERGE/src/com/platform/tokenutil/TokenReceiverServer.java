package com.platform.tokenutil;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.URI;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

public class TokenReceiverServer {

    public static void Run(String[] args) throws Exception {
        HttpServer server = HttpServer.create(new InetSocketAddress(50919), 0);
        server.createContext("/", new MyHandler());
        server.setExecutor(null); // creates a default executor
        server.start();
    }

    static class MyHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange t) throws IOException {
            String response = "Laptop Manager Extended\n\nHTTP request completed, key received. You may now close this tab.";
            URI uri = t.getRequestURI();
            System.out.println("PATH: "+uri.getQuery());
            t.sendResponseHeaders(200, response.length());
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }

}