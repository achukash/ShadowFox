import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import javax.swing.*;

public class ChatClientSwing {
    private static final String SERVER_ADDRESS = "localhost";
    private static final int SERVER_PORT = 12345;

    private static PrintWriter out;
    private static JTextArea textArea;
    private static JTextField textField;
    private static JButton sendButton;
    private static JScrollPane scrollPane;
    private static Socket socket;
    private static String clientName = "Client";  // Default client name

    public static void main(String[] args) {
        // Launch the GUI and connect to the server
        SwingUtilities.invokeLater(() -> {
            createChatClientGUI();
            connectToServer();
        });
    }

    private static void createChatClientGUI() {
        // Create the frame for the application
        JFrame frame = new JFrame("Chat Client");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 600);
        frame.setLocationRelativeTo(null); // Center the window

        // Set up the layout
        Container container = frame.getContentPane();
        container.setLayout(new BorderLayout(5, 5));

        // Chat area with a scrollable JTextArea
        textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setFont(new Font("Arial", Font.PLAIN, 14));

        scrollPane = new JScrollPane(textArea);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        // Input field and send button
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BorderLayout(5, 5));

        textField = new JTextField(30);
        textField.setFont(new Font("Arial", Font.PLAIN, 14));
        textField.addActionListener(e -> sendMessage()); // Send on Enter key

        sendButton = new JButton("Send");
        sendButton.setFont(new Font("Arial", Font.PLAIN, 14));
        sendButton.addActionListener(e -> sendMessage()); // Send on button click

        inputPanel.add(textField, BorderLayout.CENTER);
        inputPanel.add(sendButton, BorderLayout.EAST);

        // Add components to the container
        container.add(scrollPane, BorderLayout.CENTER);
        container.add(inputPanel, BorderLayout.SOUTH);

        // Ask for client name upon first connection
        String enteredName = JOptionPane.showInputDialog(frame, "Enter your name:", "Client Name", JOptionPane.PLAIN_MESSAGE);
        if (enteredName != null && !enteredName.trim().isEmpty()) {
            clientName = enteredName.trim();
        }

        // Display the frame
        frame.setVisible(true);
    }

    private static void connectToServer() {
        try {
            // Log the connection attempt
            System.out.println("Attempting to connect to the server...");

            // Try to connect to the server
            socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
            System.out.println("Connected to server!");

            // Set up input and output streams
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);

            // Start a new thread to listen for incoming messages
            new Thread(new IncomingMessageReader(in)).start();
        } catch (IOException e) {
            e.printStackTrace();
            textArea.append("Error: Unable to connect to server.\n");
        }
    }

    private static void sendMessage() {
        String message = textField.getText().trim();
        if (!message.isEmpty()) {
            String formattedMessage = clientName + ": " + message; // Format message with client name
            out.println(formattedMessage); // Send the message to the server
            textField.setText(""); // Clear the input field
        }
    }

    // Thread to handle incoming messages from the server
    private static class IncomingMessageReader implements Runnable {
        private BufferedReader in;

        public IncomingMessageReader(BufferedReader in) {
            this.in = in;
        }

        public void run() {
            String message;
            try {
                while ((message = in.readLine()) != null) {
                    textArea.append(message + "\n");
                    textArea.setCaretPosition(textArea.getDocument().getLength()); // Scroll to the bottom
                }
            } catch (IOException e) {
                System.out.println("Server disconnected or error occurred: " + e.getMessage());
                textArea.append("Connection lost to the server.\n");
                e.printStackTrace();
            } finally {
                try {
                    if (socket != null && !socket.isClosed()) {
                        socket.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
