package forth_phase.mini_projects.project_1;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

/**
 * The FileCompressor class provides functionality to compress files in a directory
 * using a thread pool for concurrent processing.
 */
class FileCompressor {

    private final ExecutorService executor;

    private final Logger logger = Logger.getLogger(FileCompressor.class.getName());

    /**
     * Constructs a FileCompressor with a specified number of threads.
     *
     * @param numThreads the number of threads to use for compression tasks
     */
    public FileCompressor(int numThreads) {
        this.executor = Executors.newFixedThreadPool(numThreads);
    }

//    /**
//     * Compresses all regular files in the specified input directory and writes
//     * the compressed files to the specified output directory.
//     *
//     * @param inputDirectory  the path to the directory containing files to compress
//     * @param outputDirectory the path to the directory where compressed files will be stored
//     * @throws IOException if an I/O error occurs while accessing the directories or files
//     */
//    public void compressDirectory(
//            String inputDirectory,
//            String outputDirectory
//    ) throws IOException {
//        // Create the output directory if it does not exist
//        Files.createDirectories(Paths.get(outputDirectory));
//
//        // List all files in the input directory
//        try (var files = Files.list(Paths.get(inputDirectory))) {
//
//            // Filter regular files and submit compression tasks to the executor
//            files.filter(Files::isRegularFile)
//                    .forEach(file -> {
//                        var outputFile = Paths.get(outputDirectory, file.getFileName().toString() + ".gz");
//                        executor.submit(new CompressionTask(file, outputFile));
//                    });
//
//        } catch (NoSuchFileException ex) {
//            logger.severe("No such directory available: " + ex.getMessage());
//        }
//    }


    public void compressAllFilesToSingleGz(
            String inputDirectory,
            String outputFile
    ) throws IOException {
        var inputDirPath = Paths.get(inputDirectory);
        var outputDirPath = Paths.get(outputFile);

        List<Path> inputFiles;

        try (var files = Files.list(inputDirPath)) {
            inputFiles = files
                    .filter(Files::isRegularFile)
                    .toList();
        }

        executor.submit(
                new CompressionTask(inputFiles, outputDirPath)
        );
    }

    /**
     * Shuts down the thread pool used for compression tasks.
     * This method should be called to release resources when the FileCompressor is no longer needed.
     */
    public void shutdown() throws InterruptedException {
        executor.shutdown();
        executor.awaitTermination(1, TimeUnit.MINUTES);
    }

}