# File Compressor (Parallel GZIP)

---

## Objective:

Compress multiple large files in parallel useing `GZIPOutputStream`.

## Concepts:

- CPU-bound task
- Use a Fixed Thread Pool
- Each thread compresses a file

## Working Steps:

1. Add a few large text or binary files to the `src/main/resources/input` directory.
2. Run the Main.java file to compress the files in parallel.
3. See the compressed files in the `src/main/resources/output` directory.