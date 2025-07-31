# Image Processor (Batch Filters)

---

## Objective:

Apply image filters (grayscale, invert) concurrently to a batch of images using fixed thread pool.

## Concepts Covered:

- `ExecutorService` with fixed thread pool
- `Callable` + `Future` for result tracking
- `BufferedImage` for image manipulation
- Batch processing pattern

## Requirements:

- Java 21 or higher
- For linux users, ensure you have the `ImageIO` library installed. If not:

```bash
  sudo apt-get install libxi6 libxtst6 libxrender1
```

- Image files