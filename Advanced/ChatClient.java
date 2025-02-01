import java.io.*;
import java.net.*;

public class ChatClient {
    private static final String SERVER_ADDRESS = "localhost";
    private static final int SERVER_PORT = 12345;
    private static PrintWriter out;

    public static void main(String[] args) {
        try (Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT)) {
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));

            // Read messages from the server in a separate thread
            new Thread(new IncomingMessageReader(in)).start();

            // Send messages to the server
            String message;
            while ((message = userInput.readLine()) != null) {
                out.println(message);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Reads incoming messages from the server
    private static class IncomingMessageReader implements Runnable {
        private BufferedReader in;

        public IncomingMessageReader(BufferedReader in) {
            this.in = in;
        }

        public void run() {
            String message;
            try {
                while ((message = in.readLine()) != null) {
                    System.out.println(message);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
