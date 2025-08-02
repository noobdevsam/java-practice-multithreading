package forth_phase.mini_projects.project_5;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.concurrent.Callable;

class FileDownloader implements Callable<String> {

    private final String fileUrl;
    private final String destinationPath;

    public FileDownloader(String destinationPath, String fileUrl) {
        this.destinationPath = destinationPath;
        this.fileUrl = fileUrl;
    }

    /**
     * Runs this operation.
     */
    @Override
    public String call() throws Exception {

        try (var in = new URL(fileUrl).openStream();
             var out = new FileOutputStream(destinationPath)) {

            byte[] buffer = new byte[4096];
            int bytesRead;

            // The bytesRead variable is used to track the number of bytes read during each iteration of the loop.
            // The read method of the input stream (in) fills the buffer with data and returns the number of bytes read.
            // If the end of the stream is reached, read returns -1, which terminates the loop:
            while ((bytesRead = in.read(buffer)) != -1) {

                // Inside the loop, the write method of the output stream (out) is called to write the data from
                // the buffer to the destination file. The method takes three arguments: the buffer, the starting
                // position (0), and the number of bytes to write (bytesRead).
                // This ensures that only the valid portion of the buffer is written:
                out.write(buffer, 0, bytesRead);
            }

            return "Downloaded: " + fileUrl;
        } catch (IOException e) {
            return "Failed: " + fileUrl + " (" + e.getMessage() + ")";
        }

    }
}
