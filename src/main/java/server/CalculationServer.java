package server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Calculation Server
 * Calculation server listens on port configured in application.properties
 * defaults to port 6565
 *
 * Server will read plain text string cleans the data and perform basic mathematical
 * operations.
 *
 * Configuration
 * client.port=6565
 * client.threads=50
 *
 * Threads relates to thread pool of clients that can connect concurrently to the
 * calculation server.
 *
 * @author James McSkeane
 * @Version 1.0
 * @Date 25-11-2019
 */

public class CalculationServer {

    // Data members
    private String message;
    private int port;
    private int threads;
    // Create Logger
    private static Logger logger = LoggerFactory.getLogger(CalculationServer.class);

    // Constructor
    private CalculationServer(int port, int threads) {
        this.port = port;
        this.threads = threads;
    }

    // Calculation thread pool will wait for clients to connect
    // Hack on while loop, FIXME come back to this
    private void start(){
        try{
            logger.info("Running on port: " + port);
            ServerSocket serverSocket = new ServerSocket(port);
            ExecutorService pool = Executors.newFixedThreadPool(threads);

            // FIXME
            while (true) {
                pool.execute(new Server(serverSocket.accept()));
            }

        } catch(IOException e) {
            logger.error("Thread loop failure " + e.toString());
        }
    }

    // Implements runnable for each thread.
    private static class Server implements Runnable {
        private Socket socket;
        Server(Socket socket){
            this.socket = socket;
        }

        // Basic message handler could of done this a number of different ways
        // Probably should of created calculation call for each thread and ensured synchronization
        // But we are not asking it to do a lot.
        private static String messageHandler(String msg){
            String errorString ="Cannot find calculation command { + * - / } Please use format +100/300";
            if (msg.length() > 0)
            {
                // I am sure there is a way to pull the operations in some kind of lambda function
                // instead of parsing individual calls FIXME come back to this.
                char operand = msg.charAt(0);
                try {
                    String[] sp = msg.substring(1).split("/", 2);
                    Double num1 = Double.parseDouble(sp[0]);
                    Double num2 = Double.parseDouble(sp[1]);

                    switch (operand){
                        case '+':
                            return String.valueOf(num1 + num2);
                        case '*':
                            return String.valueOf(num1 * num2);
                        case '-':
                            return String.valueOf(num1 - num2);
                        case '/':
                            return String.valueOf(num1 / num2);
                        default:
                            return errorString;
                    }
                } catch (NumberFormatException e) {
                    logger.error("Input string error: " + e.toString());
                    return errorString;
                }
            }
            return errorString;
        }

        @Override
        public void run(){
            // Get the current Thread ID
            long threadId = Thread.currentThread().getId();
            logger.info("Thread to client: " + threadId + " started");
            try{
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                // Call and return the message handler result
                out.println(messageHandler(in.readLine()));
            } catch (IOException e){
                logger.error("Calculation server fail " + e.toString());
            }
        }
    }

    /**
     * Create main function to run Calculation Server
     *
     * @param args
     */
    public static void main(String args[]) {
        Properties prop = new Properties();
        String propFileName = "application.properties";
        InputStream inputStream = CalculationServer.class.getClassLoader().getResourceAsStream(propFileName);
        if(inputStream != null)
        {
            try {
                prop.load(inputStream);
                int port = Integer.parseInt(prop.getProperty("client.port"));
                int threads = Integer.parseInt(prop.getProperty("client.threads"));
                logger.info("Calculation server is running.....");
                CalculationServer calculationServer = new CalculationServer(port, threads);
                calculationServer.start();
            } catch (NumberFormatException e){
                logger.error("Check application.properties for client.port and client.thread");
            } catch (IOException e)
            {
                logger.error(e.toString());
            }
        }else
            logger.error("Cannot read the " + propFileName);
    }
}
