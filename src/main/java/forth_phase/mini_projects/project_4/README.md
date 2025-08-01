# Multi-threaded Chat Server (Using CachedThread Pool)

---

## Goals:

- Handle multiple client concurrently.
- Each client handled by a separate thread from a cached thread pool.
- Broadcast messages to all connected clients.

## How to Test:

- Run the `ChatServer`
- Open multiple terminal windows.
- Use `telnet` to connect to the server:
  ```bash
  telnet localhost 12345
  ```
- Type messages in one terminal and see them broadcasted to all connected clients.