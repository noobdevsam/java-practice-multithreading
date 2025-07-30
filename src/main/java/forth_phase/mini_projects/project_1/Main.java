package forth_phase.mini_projects.project_1;

import java.io.IOException;
import java.util.logging.Logger;

public class Main {

    private static final Logger logger = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) throws IOException, InterruptedException {

        perform_compression();

    }

    private static void perform_compression() throws IOException, InterruptedException {

        try {

            var compressor = new FileCompressor(4);

            var inputDir = "src/main/resources/project1/input";
            var outputDir = "src/main/resources/project1/output";

            var start = System.currentTimeMillis();

            compressor.compressDirectory(inputDir, outputDir);

            compressor.shutdown();

            var end = System.currentTimeMillis();
            logger.info("Compression completed in " + (end - start) + " milliseconds");

        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e.getMessage());
        }

    }

}
