package forth_phase.mini_projects.project_3;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.concurrent.Callable;

/**
 * A task for applying image filters to a given input file and saving the processed image
 * to a specified output directory. This class implements the Callable interface to allow
 * concurrent execution of image processing tasks.
 */
class ImageFilterTask implements Callable<String> {

    private final File inputFile; // The input image file to be processed
    private final File outputDir; // The directory where the processed image will be saved
    private final String filterName; // The name of the filter to be applied

    /**
     * Constructs an ImageFilterTask with the specified input file, output directory, and filter name.
     *
     * @param inputFile  The input image file to be processed.
     * @param outputDir  The directory where the processed image will be saved.
     * @param filterName The name of the filter to be applied (e.g., "grayscale", "invert").
     */
    public ImageFilterTask(File inputFile, File outputDir, String filterName) {
        this.inputFile = inputFile;
        this.outputDir = outputDir;
        this.filterName = filterName;
    }

    /**
     * Executes the image filter task and returns the result as a string.
     *
     * @return A string indicating the success or failure of the filter application.
     * @throws Exception If an error occurs during image processing.
     *                   <p>
     *                   This method reads the input image file, applies the specified filter using the FilterUtils class,
     *                   and saves the processed image to the output directory. If the filter name is unsupported or an
     *                   error occurs, an appropriate error message is returned.
     */
    @Override
    public String call() throws Exception {
        try {
            // Read the input image file
            BufferedImage original = ImageIO.read(inputFile);
            BufferedImage processed;

            // Apply the specified filter based on the filter name
            switch (filterName.toLowerCase()) {
                case "grayscale" -> processed = FilterUtils.applyGrayScale(original); // Apply grayscale filter
                case "invert" -> processed = FilterUtils.applyInvert(original); // Apply invert filter
                default -> {
                    // Return an error message for unsupported filters
                    return inputFile.getName() + ": Error - Unsupported filter: " + filterName;
                }
            }

            // Create the output file in the specified directory
            var outputFile = new File(outputDir, inputFile.getName());

            // Write the processed image to the output file
            ImageIO.write(processed, "png", outputFile);

            // Return a success message
            return outputFile.getName() + ": Filter applied successfully with " + filterName + " filter.";

        } catch (Exception e) {
            // Return an error message if an exception occurs
            return inputFile.getName() + ": Error - " + e.getMessage();
        }
    }
}