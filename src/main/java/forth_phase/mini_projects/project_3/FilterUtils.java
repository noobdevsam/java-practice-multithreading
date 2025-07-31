package forth_phase.mini_projects.project_3;

import java.awt.image.BufferedImage;

/**
 * Utility class for applying image filters.
 */
class FilterUtils {

    /**
     * Applies a grayscale filter to the given image.
     *
     * @param original The original BufferedImage to be processed.
     * @return A new BufferedImage with the grayscale filter applied.
     * <p>
     * The grayscale filter calculates the average of the red, green, and blue
     * components of each pixel and sets all three components to this average,
     * resulting in a grayscale image.
     */
    public static BufferedImage applyGrayScale(BufferedImage original) {
        // Create a new BufferedImage to store the result
        var result = new BufferedImage(
                original.getWidth(), original.getHeight(), BufferedImage.TYPE_INT_RGB
        );

        // Iterate over each pixel in the original image
        for (int y = 0; y < original.getHeight(); y++) {

            for (int x = 0; x < original.getWidth(); x++) {

                // Get the RGB value of the current pixel
                var rgb = original.getRGB(x, y);

                // Extract the red, green, and blue components
                /*
                 * The below code snippet, `var r = (rgb >> 16) & 0xFF;`, is part of a process to extract the
                 * red component from a pixel's RGB value in an image. Here's how it works:
                 *
                 * In Java, the `getRGB()` method retrieves the color of a pixel as a single integer value, where the
                 * red, green, and blue components are packed into the 32-bit integer.
                 * The format is typically `0xAARRGGBB`, where `AA` represents the alpha (transparency),
                 * `RR` is the red component, `GG` is the green component, and `BB` is the blue component.
                 *
                 * To isolate the red component, the code uses a bitwise operation. First, the `rgb` value is
                 * shifted 16 bits to the right using the `>>` operator:
                 * [rgb >> 16]
                 *
                 * This operation moves the red component into the least significant byte (the rightmost 8 bits),
                 * effectively discarding the green and blue components.
                 *
                 * Next, the result is masked with `0xFF` using the bitwise AND operator (`&`):
                 * [(rgb >> 16) & 0xFF]
                 *
                 * The mask `0xFF` ensures that only the least significant byte (the red component) is retained,
                 * while any remaining bits are cleared.
                 * The final value stored in `r` is the intensity of the red component for the pixel, represented as
                 * an integer between 0 and 255.
                 * */
                var r = (rgb >> 16) & 0xFF;

                // Extract the green component with a similar process
                var g = (rgb >> 8) & 0xFF;

                // Extract the blue component with a similar process
                var b = rgb & 0xFF;

                // Calculate the grayscale value as the average of the RGB components
                var gray = (r + g + b) / 3;

                // Create a new RGB value where all components are set to the grayscale value
                // This is done by shifting the gray value to the appropriate positions
                // in the RGB format (0xRRGGBB)
                var newRgb = (gray << 16) | (gray << 8) | gray;

                // Set the new RGB value in the result image
                result.setRGB(x, y, newRgb);
            }
        }

        // Return the processed image
        return result;
    }
}