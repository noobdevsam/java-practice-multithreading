package forth_phase.mini_projects.project_1;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.logging.Logger;
import java.util.zip.GZIPOutputStream;

/**
 * A task that compresses a file using GZIP compression.
 * Implements the Runnable interface to allow execution in a separate thread.
 */
class CompressionTask implements Runnable {

    // private final Path inputFile; // Path to the input file to be compressed
    private final List<Path> inputFiles; // List of paths to the files to be compressed
    private final Path outputFile; // Path to the output file where compressed data will be written

    private final Logger logger = Logger.getLogger(CompressionTask.class.getName()); // Logger for logging messages

    /**
     * Constructs a CompressionTask with the specified input and output file paths.
     *
     * @param inputFiles the path to the files to be compressed
     * @param outputFile the path to the file where the compressed data will be written
     */
    // public CompressionTask(Path inputFile, Path outputFile) {
    public CompressionTask(List<Path> inputFiles, Path outputFile) {
        // this.inputFile = inputFile;
        this.inputFiles = inputFiles;
        this.outputFile = outputFile;
    }

// Uncomment the following method if you want to compress a single file
//    /**
//     * Executes the file compression task.
//     * Reads the input file, compresses its contents using GZIP, and writes the compressed data to the output file.
//     * Logs the success or failure of the operation.
//     */
//    @Override
//    public void run() {
//        try (
//                var in = Files.newInputStream(inputFile); // Input stream for reading the input file
//                var out = Files.newOutputStream(outputFile); // Output stream for writing the compressed file
//                var gzipOut = new GZIPOutputStream(out) // GZIP output stream for compression
//        ) {
//            byte[] buffer = new byte[8192]; // Buffer for reading data in chunks
//            int len;
//
//            // Read data from the input file and write compressed data to the output file
//            while ((len = in.read(buffer)) > 0) {
//                gzipOut.write(buffer, 0, len);
//            }
//
//            // Log successful compression
//            logger.info(Thread.currentThread().getName()
//                    + " compressed file: " + inputFile.getFileName());
//
//        } catch (IOException e) {
//            // Log failure with error details
//            logger.severe("Failed to compress file: " + inputFile.getFileName() + ": " + e.getMessage());
//        }
//    }


    /**
     * Runs this operation.
     */
    @Override
    public void run() {

        try (
                var out = Files.newOutputStream(outputFile); // Output stream for writing the compressed file
                var gzipOut = new GZIPOutputStream(out) // GZIP output stream for compression
        ) {
            byte[] buffer = new byte[8192]; // Buffer for reading data in chunks

            for (var inputFile : inputFiles) {
                try (var in = Files.newInputStream(inputFile)) {
                    int len;

                    while ((len = in.read(buffer)) > 0) {
                        gzipOut.write(buffer, 0, len); // Write compressed data to the output file
                    }

                    logger.info("Added file: " + inputFile.getFileName() + " to " + outputFile.getFileName());
                } catch (IOException ex) {
                    logger.severe("Failed to compress file: " + inputFile.getFileName() + ": " + ex.getMessage());
                }
            }

            logger.info("All files compressed into: " + outputFile.getFileName());

        } catch (IOException e) {
            logger.severe("Failed to compress files: " + inputFiles + ": " + e.getMessage());
        }
    }
}