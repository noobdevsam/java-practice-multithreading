package fifth_phase.mini_project_1;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.Executors;

public class VirtualFileDownloaderImplA implements VirtualFileDownloader {

    @Override
    public void simpleDownloadFile(String urlString) {
        var fileName = Paths.get(URI.create(urlString).getPath()).getFileName().toString();

        System.out.println("Starting download: " + fileName + " on: " + Thread.currentThread());

        try (var in = URI.create(urlString).toURL().openStream()) {
            // Ensure the downloads directory exists in the current working directory
            Files.createDirectories(Paths.get("downloads"));
            Files.copy(in, Paths.get("downloads", fileName));
            System.out.println("Downloaded = " + fileName);
        } catch (IOException e) {
            System.err.println("Error downloading: " + urlString + " - " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        List<String> urls = List.of(
                "https://example.com/file1.txt",
                "https://example.com/file2.txt"
        );

        try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
            for (var url : urls) {
                executor.submit(
                        () -> new VirtualFileDownloaderImplA().simpleDownloadFile(url)
                );
            }

            executor.shutdown();
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }
}
