# Concurrent Logger

## Goal

Implement a thread-safe logger where multiple threads enqueue log massages,
and a single dedicated writer thread writes logs sequentially to a file,
ensuring order and no file corruption.

## Concepts Practiced

- Producer-Consumer Pattern
- `BlockingQueue` (e.g., `LinkedBlockingQueue`)
- Single dedicated consumer thread
- Graceful shutdown