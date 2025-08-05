# Virtual Threads + `CompletableFuture` (Chaining + Async Workflows)

---

Practice using virtual threads with `CompletableFuture` to build clean, async-style pipelines ---without callbacks or
threads bloat.

## Concepts Practiced

- Launching `CompletableFuture` tasks on virtual threads.
- Chaining `.thenApply`, `thenAccept`, `exceptionally`
- Mixing `Executors.newVirtualThreadPerTaskExecutor()` for custom execution.

## Notes

- Each step runs in a separate virtual thread.
- Exception handling is clean and non-blocking.
- Great pattern for microservices orchestration, async pipelines, background flows.