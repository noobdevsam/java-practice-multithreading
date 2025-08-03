package fifth_phase.mini_project_1;

import java.io.IOException;

public class VirtualFileDownloaderImplB implements VirtualFileDownloader {

    private static final int MAX_RETRIES = 3;

    // performs the download operation with retry logic
    private static void downloadWithRetry(String urlString) {

        for (int attempt = 0; attempt <= MAX_RETRIES; attempt++) {
            try {
                downloadWithRetry(urlString);
                return;
            } catch (IOException e) {
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
        throw new IOException("Not implemented");
    }
}
