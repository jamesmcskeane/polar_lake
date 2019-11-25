package com.Bloomberg.bloomberg.service;
import server.CalculationServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.io.*;
import java.net.Socket;

/**
 *  Client Service
 *  This is a very simple service, usually we would create models and interface calls
 *  to a number of different microservices
 *  we are not doing anything where wth databases just a simple call to a calculation
 *  server.
 *
 * @Athor James McSkeane
 * @Version 1.0
 * @Date 25-11-2019
 */

@Service
public class ClientService {
    //  Create logger
    private static Logger logger = LoggerFactory.getLogger(CalculationServer.class);
    // Get value from application.properties
    @Value("${client.hostname}")
    private String host;
    // Get value from application.properties
    @Value("${client.port}")
    private int port;

    // setters are for unit tests
    public void setHost(String host) {
        this.host = host;
    }

    public void setPort(int port) {
        this.port = port;
    }

    // Default constructor
    public ClientService() {
    }

    // make call to calculation server
    public String sendToCalculationServer(String raw){
        Socket socket;
        PrintWriter out;
        BufferedReader in;
        String result;
        try {
            logger.info("Connecting to host: " + host + " and port: " + port);
            socket = new Socket(host, port);
            logger.info("Connected to host: " + host + " and port: " + port);
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            logger.info("Sending calculation to calculation server: " + raw);
            out.println(raw);
            logger.info("Waiting on calculation server.... " + raw);
            result = in.readLine();
            logger.info("Received response : " + result);
            in.close();
            out.close();
            logger.info("Closing socket");
            socket.close();
        } catch (IOException e){
            String errorString = "Error connecting to calculation server";
            logger.error(errorString + e.toString());
            return errorString;
        }
        // Return result from calculation server
        return result;
    }
}
