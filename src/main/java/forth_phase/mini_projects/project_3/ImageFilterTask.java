package forth_phase.mini_projects.project_3;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.concurrent.Callable;

class ImageFilterTask implements Callable<String> {

    private final File inputFile;
    private final File outputDir;
    private final String filterName;

    public ImageFilterTask(File inputFile, File outputDir, String filterName) {
        this.inputFile = inputFile;
        this.outputDir = outputDir;
        this.filterName = filterName;
    }

    /**
     * Computes a result, or throws an exception if unable to do so.
     *
     * @return computed result
     * @throws Exception if unable to compute a result
     */
    @Override
    public String call() throws Exception {
        try {

            BufferedImage original = ImageIO.read(inputFile);
            BufferedImage processed;

            switch (filterName.toLowerCase()) {
                case "grayscale" -> processed = FilterUtils.applyGrayScale(original);
                case "invert" -> processed = FilterUtils.applyInvert(original);
                default -> {
                    return inputFile.getName() + ": Error - Unsupported filter: " + filterName;
                }
            }

            var outputFile = new File(outputDir, inputFile.getName());

            ImageIO.write(processed, "png", outputFile);

            return outputFile.getName() + ": Filter applied successfully with " + filterName + " filter.";

        } catch (Exception e) {
            return inputFile.getName() + ": Error - " + e.getMessage();
        }
    }
}
