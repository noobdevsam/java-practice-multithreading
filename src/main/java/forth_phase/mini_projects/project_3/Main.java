package forth_phase.mini_projects.project_3;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class Main {

    public static void main(String[] args) throws IOException {

        var inputDir = new File("src/main/resources/project3/input");
        var outputDir = new File("src/main/resources/project3/output");


        if (!inputDir.exists() || !inputDir.isDirectory() || !outputDir.exists() || !outputDir.isDirectory()) {
            Files.createDirectories(Paths.get(inputDir.getAbsolutePath()));
            Files.createDirectories(Paths.get(outputDir.getAbsolutePath()));
        }

        var filterName = "invert";

        var images = inputDir.listFiles(
                (dir, name) -> name.endsWith(".jpg") || name.endsWith(".png")
        );


        if (images == null || images.length == 0) {
            System.out.println("No images found in the input directory.");
        }

        List<Future<String>> results = new ArrayList<>();

        try (var executor = Executors.newFixedThreadPool(4)) {
            for (var image : images) {
                results.add(
                        executor.submit(
                                new ImageFilterTask(image, outputDir, filterName)
                        )
                );
            }

            executor.shutdown();
            executor.awaitTermination(2, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            throw new RuntimeException("Image processing interrupted", e);
        }

        for (Future<String> result : results) {
            try {
                System.out.println(result.get());
            } catch (Exception e) {
                System.err.println("Error processing image: " + e.getMessage());
            }
        }

        System.out.println("Image processing completed.");
    }

}
