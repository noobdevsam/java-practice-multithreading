package forth_phase.mini_projects.project_4;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;

/**
 * Handles communication with a single client in a chat server.
 *
 * <p>This class implements the Runnable interface to allow concurrent handling
 * of multiple clients. Each client is managed by an instance of this class.
 *
 * <ul>
 *   <li>Receives messages from the client and broadcasts them to other clients.</li>
 *   <li>Manages the lifecycle of the client connection, including cleanup on disconnect.</li>
 * </ul>
 */
class ClientHandler implements Runnable {

    private final Socket socket; // The socket for communication with the client.
    private final List<ClientHandler> clients; // List of all connected clients.
    private PrintWriter out; // Output stream to send messages to the client.

    /**
     * Constructs a new ClientHandler.
     *
     * @param socket  The socket for communication with the client.
     * @param clients The list of all connected clients.
     */
    public ClientHandler(Socket socket, List<ClientHandler> clients) {
        this.socket = socket;
        this.clients = clients;
    }

    /**
     * Runs the client handler logic.
     *
     * <p>Handles incoming messages from the client, broadcasts them to other clients,
     * and manages the connection lifecycle.
     */
    @Override
    public void run() {
        try (
                var in = new BufferedReader(
                        new InputStreamReader(
                                socket.getInputStream()
                        )
                )
        ) {
            out = new PrintWriter(socket.getOutputStream(), true);
            out.println("Welcome to the chat server! Type 'exit' to leave.");

            String message;
            while ((message = in.readLine()) != null) {
                if ("exit".equalsIgnoreCase(message)) {
                    break; // Exit the loop if the client sends 'exit'.
                }
                broadcast(message); // Broadcast the message to other clients.
            }
        } catch (IOException e) {
            throw new RuntimeException(e); // Handle I/O errors during communication.
        } finally {
            clients.remove(this); // Remove this client from the list of connected clients.
            try {
                socket.close(); // Close the socket connection.
            } catch (IOException e) {
                System.err.println("Error closing socket: " + e.getMessage());
            }
            System.out.println("Client disconnected."); // Log client disconnection.
        }
    }

    /**
     * Broadcasts a message to all connected clients except the sender.
     *
     * @param message The message to broadcast.
     */
    private void broadcast(String message) {
        for (ClientHandler client : clients) {
            if (!client.equals(this)) { // Skip the sender.
                client.send("Client: " + message); // Send the message to the client.
            }
        }
    }

    /**
     * Sends a message to this client.
     *
     * @param message The message to send.
     */
    private void send(String message) {
        if (out != null) {
            out.println(message); // Send the message via the output stream.
        }
    }

}