package fifth_phase.mini_project_1;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;

public class VirtualFileDownloaderImplA implements VirtualFileDownloader {

    @Override
    public void simpleDownloadFile(String urlString) {
        var fileName = Paths.get(URI.create(urlString).getPath()).getFileName().toString();

        System.out.println("Starting download: " + fileName + " on: " + Thread.currentThread());

        try (var in = URI.create(urlString).toURL().openStream()) {
            Files.copy(in, Paths.get("downloads", fileName));
            System.out.println("Downloaded = " + fileName);
        } catch (IOException e) {
            System.err.println("Error downloading: " + urlString + " - " + e.getMessage());
        }
    }
}
