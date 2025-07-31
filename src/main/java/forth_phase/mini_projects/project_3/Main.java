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

        /**
         * Processes images from an input directory, applies a filter, and saves the results to an output directory.
         *
         * - Ensures the input and output directories exist, creating them if necessary.
         * - Filters images based on file extensions (.jpg, .png).
         * - Applies the specified filter to each image using a thread pool for concurrent processing.
         * - Handles errors during image processing and logs the results.
         */

        var inputDir = new File("src/main/resources/project3/input");
        var outputDir = new File("src/main/resources/project3/output");

        // Ensure the input and output directories exist, creating them if they don't.
        if (!inputDir.exists() || !inputDir.isDirectory() || !outputDir.exists() || !outputDir.isDirectory()) {
            Files.createDirectories(Paths.get(inputDir.getAbsolutePath()));
            Files.createDirectories(Paths.get(outputDir.getAbsolutePath()));
        }

        // Name of the filter to be applied to the images.
        var filterName = "invert";

        // List all image files in the input directory with extensions .jpg or .png.
        var images = inputDir.listFiles(
                (_, name) -> name.endsWith(".jpg") || name.endsWith(".png")
        );

        // If no images are found, log a message and exit.
        if (images == null || images.length == 0) {
            System.out.println("No images found in the input directory.");
        }

        // List to store the results of image processing tasks.
        List<Future<String>> results = new ArrayList<>();

        // Use a thread pool to process images concurrently.
        try (var executor = Executors.newFixedThreadPool(4)) {
            for (var image : images) {
                // Submit image processing tasks to the executor.
                results.add(
                        executor.submit(
                                new ImageFilterTask(image, outputDir, filterName)
                        )
                );
            }

            // Shut down the executor and wait for all tasks to complete.
            executor.shutdown();
            executor.awaitTermination(2, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            // Handle interruption during image processing.
            throw new RuntimeException("Image processing interrupted", e);
        }

        // Retrieve and log the results of each image processing task.
        for (Future<String> result : results) {
            try {
                System.out.println(result.get());
            } catch (Exception e) {
                // Log any errors that occur during image processing.
                System.err.println("Error processing image: " + e.getMessage());
            }
        }

        // Log a message indicating that image processing is complete.
        System.out.println("Image processing completed.");

    }

}