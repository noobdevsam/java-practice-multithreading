package forth_phase.mini_projects.project_2;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.logging.Logger;

/**
 * A task that crawls a given URL to a specified depth, extracting links and submitting new tasks for further crawling.
 */
class CrawlTask implements Runnable {

    private final String url; // The URL to crawl
    private final int depth; // The remaining depth for crawling
    private final Set<String> urlsVisited; // A set of URLs that have already been visited
    private final ExecutorService executor; // Executor service for submitting new crawl tasks
    private final Logger logger = Logger.getLogger(CrawlTask.class.getName()); // Logger for logging crawl activity

    /**
     * Constructs a CrawlTask.
     *
     * @param url         The URL to crawl.
     * @param depth       The depth of the crawl (number of levels to traverse).
     * @param urlsVisited A set of URLs that have already been visited to avoid duplicates.
     * @param executor    The executor service for managing concurrent tasks.
     */
    public CrawlTask(
            String url,
            int depth,
            Set<String> urlsVisited,
            ExecutorService executor
    ) {
        this.url = url;
        this.depth = depth;
        this.urlsVisited = urlsVisited;
        this.executor = executor;
    }

    /**
     * Executes the crawl task.
     * <p>
     * Stops if the depth is zero or the URL has already been visited.
     * Fetches the document at the URL, extracts links, and submits new tasks for unvisited links.
     */
    @Override
    public void run() {

        // Base case: stop if depth is 0 or URL has already been visited
        if (depth == 0 || urlsVisited.contains(url)) {
            return;
        }
        urlsVisited.add(url); // Mark this URL as visited

        logger.info("[" + Thread.currentThread().getName() + "] visiting: " + url);

        try {
            // Fetch the document at the URL
            Document doc = Jsoup
                    .connect(url)
                    .timeout(3000) // Set timeout for the connection
                    .get();

            // Extract all links from the document
            Elements links = doc.select("a[href]");

            // Iterate through each link found in the document
            // and submit a new task for each unvisited link
            // to continue crawling
            // at the next depth level
            for (var link : links) {
                String absoluteUrl = link.absUrl("href"); // Get the absolute URL of the link

                // Submit a new task for unvisited links
                if (!absoluteUrl.isEmpty() && urlsVisited.add(absoluteUrl)) {
                    executor.submit(
                            new CrawlTask(absoluteUrl, depth - 1, urlsVisited, executor)
                    );
                }
            }
        } catch (Exception e) {
            // Log any errors encountered during the crawl
            logger.severe("Error fetching: " + url + " - " + e.getMessage());
        }
    }
}