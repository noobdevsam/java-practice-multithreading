package fifth_phase.mini_project_1;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.Executors;

public class VirtualFileDownloaderImplB implements VirtualFileDownloader {

    private static final int MAX_RETRIES = 3;

    public static void main(String[] args) {
        List<String> urls = List.of(
                "https://example.com/file1.txt",
                "https://example.com/file2.txt",
                "https://example.com/file3.txt"
        );

        try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
            for (var url : urls) {
                executor.submit(() -> {
                    downloadWithRetry(url);
                });
            }

            executor.shutdown();
        }
    }

    // performs the download operation with retry logic
    private static void downloadWithRetry(String urlString) {

        for (int attempt = 0; attempt <= MAX_RETRIES; attempt++) {
            try {
                new VirtualFileDownloaderImplB().simpleDownloadFile(urlString);
                return;
            } catch (Exception e) {
                System.err.printf("Attempt %d failed for %s: %s%n", attempt, urlString, e.getMessage());

                if (attempt == MAX_RETRIES) {
                    System.err.printf("Max retries reached for %s. Giving up.%n", urlString);
                } else {
                    try {
                        Thread.sleep(1000L * attempt); // Exponential backoff
                    } catch (InterruptedException ignored) {
                    }
                }
            }
        }

    }

    // Implementation of the downloadFile method with retry logic
    @Override
    public void simpleDownloadFile(String urlString) throws IOException {
        if (urlString == null || urlString.isEmpty()) {
            throw new IllegalArgumentException("URL cannot be null or empty");
        }

        var url = URI.create(urlString).toURL();
        var fileName = Paths.get(url.getPath()).getFileName().toString();
        var outputPath = Paths.get("downloads", fileName);
        Files.createDirectories(outputPath.getParent()); // Ensure the parent directory exists

        System.out.println("Downloading " + fileName + "[ " + Thread.currentThread() + " ]");

        var connection = (HttpURLConnection) url.openConnection();
        var contentLength = connection.getContentLengthLong();

        try (
                var in = new BufferedInputStream(connection.getInputStream());
                var out = new FileOutputStream(outputPath.toFile())
        ) {
            byte[] buffer = new byte[8192];
            int bytesRead;
            long totalBytesRead = 0;

            while ((bytesRead = in.read(buffer)) != -1) {
                out.write(buffer, 0, bytesRead); // Write bytes to file
                totalBytesRead += bytesRead; // Update total bytes read

                if (contentLength > 0) {
                    int progress = (int) ((totalBytesRead * 100) / contentLength); // Calculate progress percentage
                    System.out.printf("Progress %s: %d%%\n ", fileName, progress);
                } else {
                    System.out.printf("Progress %s: %d KB\n ", fileName, (totalBytesRead / 1024)); // Display progress in KB
                }
            }
        }

        // Close the connection
        connection.disconnect();

        System.out.println("\nDownload completed: " + fileName);

        // Check if the file was downloaded successfully
        if (Files.exists(outputPath)) {
            // display downloaded file location
            System.out.println("File saved to: " + outputPath.toAbsolutePath());
        }
    }
}
