# More use of `ReentrantLock` with virtual threads for fairness and performance

---

Let’s explore how to use `tryLock()` and understand lock fairness using `ReentrantLock`.

ReentrantLock Deep Dive: ReentrantLock gives you explicit control over locking, beyond what synchronized provides.

## tryLock() – Non-blocking Lock Attempt

Why use it?

- Avoid thread waiting forever (like with lock())
- Try lock for a certain time period, then back off

## Fairness in ReentrantLock

By default, `ReentrantLock` is non-fair, meaning a thread can "cut the line" if it gets lucky.

You can force fairness using:

```java
ReentrantLock lock = new ReentrantLock(true); // fair mode
```

Fair mode ensures first-come, first-served, but:

- Slightly slower
- Prevents thread starvation