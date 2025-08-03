package fifth_phase.mini_project_1;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.Executors;

/**
 * Implementation of the VirtualFileDownloader interface.
 * Provides functionality to download files from URLs and save them locally.
 */
public class VirtualFileDownloaderImplA implements VirtualFileDownloader {

    /**
     * Main method to demonstrate downloading multiple files using virtual threads.
     *
     * @param args Command-line arguments (not used).
     */
    public static void main(String[] args) {
        // List of URLs to download
        List<String> urls = List.of(
                "https://example.com/file1.txt",
                "https://example.com/file2.txt"
        );

        // Use a virtual thread executor to download files concurrently
        try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
            for (var url : urls) {
                // Submit each download task to the executor
                executor.submit(
                        () -> new VirtualFileDownloaderImplA().simpleDownloadFile(url)
                );
            }

            // Shutdown the executor after tasks are submitted
            executor.shutdown();
        } catch (RuntimeException e) {
            // Handle any runtime exceptions
            throw new RuntimeException(e);
        }
    }

    /**
     * Downloads a file from the given URL and saves it to the "downloads" directory.
     *
     * @param urlString The URL of the file to download.
     */
    @Override
    public void simpleDownloadFile(String urlString) {
        // Extract the file name from the URL
        var fileName = Paths.get(URI.create(urlString).getPath()).getFileName().toString();

        // Log the start of the download process
        System.out.println("Starting download: " + fileName + " on: " + Thread.currentThread());

        try (var in = URI.create(urlString).toURL().openStream()) {
            // Ensure the "downloads" directory exists
            Files.createDirectories(Paths.get("downloads"));
            // Copy the file from the URL to the "downloads" directory
            Files.copy(in, Paths.get("downloads", fileName));
            System.out.println("Downloaded = " + fileName);
        } catch (IOException e) {
            // Log any errors that occur during the download process
            System.err.println("Error downloading: " + urlString + " - " + e.getMessage());
        }
    }
}