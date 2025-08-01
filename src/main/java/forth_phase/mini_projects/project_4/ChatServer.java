package forth_phase.mini_projects.project_4;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Executors;

/**
 * Represents a chat server that handles multiple client connections.
 *
 * <p>The server listens on a specified port and uses a thread pool to manage
 * client connections concurrently. Each client is handled by a `ClientHandler`
 * instance, which manages communication and message broadcasting.</p>
 */
public class ChatServer {

    private static final int PORT = 12345; // Port number for the chat server.
    private static final CopyOnWriteArrayList<ClientHandler> clients = new CopyOnWriteArrayList<>(); // Thread-safe list of connected clients.

    /**
     * The main method to start the chat server.
     *
     * <p>Initializes the server socket, accepts client connections, and assigns
     * each client to a `ClientHandler` for communication. Uses a cached thread
     * pool to handle client connections concurrently.</p>
     *
     * @param args Command-line arguments (not used).
     */
    public static void main(String[] args) {

        var threadPool = Executors.newCachedThreadPool(); // Thread pool for managing client handlers.

        try (
                var serverSocket = new ServerSocket(PORT) // Server socket to listen for client connections.
        ) {
            System.out.println("Chat server started on port " + PORT);

            while (true) {
                var clientSocket = serverSocket.accept(); // Accept a new client connection.
                var clientHandler = new ClientHandler(clientSocket, clients); // Create a handler for the client.
                clients.add(clientHandler); // Add the client handler to the list of connected clients.
                threadPool.execute(clientHandler); // Execute the client handler in the thread pool.
            }

        } catch (IOException e) {
            throw new RuntimeException(e); // Handle I/O errors during server operation.
        } finally {
            threadPool.shutdown(); // Shut down the thread pool.
        }

    }

}