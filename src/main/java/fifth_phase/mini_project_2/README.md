# Asynchronous Task Scheduler Using Virtual Threads

---

Build a simple scheduler that runs delayed or periodic tasks using virtual threads.

## Concepts Practiced

- Scheduled tasks with `ScheduledExecutorService`
- Offloading task logic to virtual threads inside scheduled events
- Combining scheduling + concurrency

## What we'll build

- Every `X` seconds, a task runs (e.g., ping a server, print timestamp, rotate logs).
- The task body runs inside a virtual thread - keeping it lightweight and responsive.