package forth_phase.mini_projects.project_4;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Executors;

public class ChatServer {

    private static final int PORT = 12345;
    private static final CopyOnWriteArrayList<ClientHandler> clients = new CopyOnWriteArrayList<>();

    public static void main(String[] args) {

        var threadPool = Executors.newCachedThreadPool();

        try (

                var serverSocket = new ServerSocket(PORT)
        ) {
            System.out.println("Chat server started on port " + PORT);

            while (true) {
                var clientSocket = serverSocket.accept();
                var clientHandler = new ClientHandler(clientSocket, clients);
                clients.add(clientHandler);
                threadPool.execute(clientHandler);
            }


        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            threadPool.shutdown();
            threadPool.close();
        }

    }

}
