package forth_phase.mini_projects.project_5;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class DownloaderApp {

    public static void main(String[] args) {

        // Replace these URLs with the actual file URLs you want to download.
        // The destination file names will be generated as "downloaded_file_1.txt", "downloaded_file_2.txt", etc.
        // Ensure that the URLs point to valid text files
        // that can be downloaded without authentication or special headers.
        // The files will be saved in the current working directory of the application.
        // You can change the destination file names as needed.
        // The application uses a thread pool to download files concurrently.
        // It creates a separate thread for each file download task.
        // The downloaded files will be saved in the current working directory with names like "downloaded_file_1.txt", "downloaded_file_2.txt", etc.
        // The application handles exceptions during the download process and prints error messages if any download fails.

        List<String> urls = List.of(
                "https://example.com/file-a.txt",
                "https://example.com/file-b.txt"
        );

        // Using CopyOnWriteArrayList to store futures for thread-safe concurrent access
        // This allows us to safely add futures from multiple threads without additional synchronization.
        List<Future<String>> futures = new CopyOnWriteArrayList<>();

        try (var executor = Executors.newCachedThreadPool()) {
            for (int i = 0; i < urls.size(); i++) {

                // For each URL in the list, we create a FileDownloader task.
                // The destination file name is generated based on the index of the URL in the list.

                // Get the URL and create a FileDownloader task for it.
                var url = urls.get(i);

                // Generate a destination file name based on the index.
                // This will create files named "downloaded_file_1.txt", "downloaded_file_2.txt", etc.
                // You can modify the naming convention as needed.
                // The destination file will be saved in the current working directory.
                // Ensure that the destination file names do not conflict with existing files.
                var destination = "downloaded_file_" + (i + 1) + ".txt";
                var task = new FileDownloader(destination, url);

                // Submit the task to the executor and add the future to the list.
                // The future represents the result of the asynchronous computation.
                // It allows us to retrieve the result of the download operation once it completes.
                // The futures list will hold the results of all download tasks.
                futures.add(executor.submit(task));
            }

            futures.forEach(future -> {
                try {
                    // Wait for each future to complete and print the result.
                    // The get() method blocks until the result is available.
                    System.out.println("Downloaded: " + future.get());
                } catch (Exception e) {
                    System.err.println("Error downloading file: " + e.getMessage());
                }
            });

            // Shutdown the executor to release resources.
            // This will stop accepting new tasks and wait for existing tasks to complete.
            executor.shutdown();
        }

    }

}
