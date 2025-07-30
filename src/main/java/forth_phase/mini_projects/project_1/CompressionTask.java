package forth_phase.mini_projects.project_1;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.logging.Logger;
import java.util.zip.GZIPOutputStream;

/**
 * A task that compresses a file using GZIP compression.
 * Implements the Runnable interface to allow execution in a separate thread.
 */
class CompressionTask implements Runnable {

    private final Path inputFile; // Path to the input file to be compressed
    private final Path outputFile; // Path to the output file where compressed data will be written

    private final Logger logger = Logger.getLogger(CompressionTask.class.getName()); // Logger for logging messages

    /**
     * Constructs a CompressionTask with the specified input and output file paths.
     *
     * @param inputFiles the path to the files to be compressed
     * @param outputFile the path to the file where the compressed data will be written
     */
    // public CompressionTask(Path inputFile, Path outputFile) {
    public CompressionTask(Path inputFile, Path outputFile) {
        this.inputFile = inputFile;
        this.outputFile = outputFile;
    }

    /**
     * Executes the file compression task.
     * Reads the input file, compresses its contents using GZIP, and writes the compressed data to the output file.
     * Logs the success or failure of the operation.
     */
    @Override
    public void run() {
        try (
                var in = Files.newInputStream(inputFile); // Input stream for reading the input file
                var out = Files.newOutputStream(outputFile); // Output stream for writing the compressed file
                var gzipOut = new GZIPOutputStream(out) // GZIP output stream for compression
        ) {
            byte[] buffer = new byte[8192]; // Buffer for reading data in chunks
            int len;

            // Read data from the input file and write compressed data to the output file
            while ((len = in.read(buffer)) > 0) {
                gzipOut.write(buffer, 0, len);
            }

            // Log successful compression
            logger.info(Thread.currentThread().getName()
                    + " compressed file: " + inputFile.getFileName());

        } catch (IOException e) {
            // Log failure with error details
            logger.severe("Failed to compress file: " + inputFile.getFileName() + ": " + e.getMessage());
        }
    }

}