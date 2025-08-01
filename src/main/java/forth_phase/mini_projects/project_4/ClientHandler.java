package forth_phase.mini_projects.project_4;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;

class ClientHandler implements Runnable {

    private final Socket socket;
    private final List<ClientHandler> clients;
    private PrintWriter out;

    public ClientHandler(Socket socket, List<ClientHandler> clients) {
        this.socket = socket;
        this.clients = clients;
    }

    /**
     * Runs this operation.
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
                    break;
                }
                broadcast(message);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            clients.remove(this);
            try {
                socket.close();
            } catch (IOException e) {
                System.err.println("Error closing socket: " + e.getMessage());
            }
            System.out.println("Client disconnected.");
        }
    }

    private void broadcast(String message) {
        for (ClientHandler client : clients) {
            if (!client.equals(this)) {
                client.send("Client: " + message);
            }
        }
    }

    private void send(String message) {
        if (out != null) {
            out.println(message);
        }
    }

}
