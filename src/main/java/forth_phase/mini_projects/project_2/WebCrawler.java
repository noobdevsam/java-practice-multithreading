package forth_phase.mini_projects.project_2;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * A web crawler that uses multithreading to crawl web pages starting from a seed URL.
 * It tracks visited URLs and limits the depth of the crawl.
 */
class WebCrawler {
    private final ExecutorService executorService; // Executor service for managing threads
    private final Set<String> visitedUrls = ConcurrentHashMap.newKeySet(); // Thread-safe set to store visited URLs

    /**
     * Constructs a WebCrawler with a specified maximum number of threads.
     *
     * @param maxThreads The maximum number of threads to use for crawling.
     */
    public WebCrawler(int maxThreads) {
        this.executorService = Executors.newFixedThreadPool(maxThreads);
    }

    /**
     * Starts the crawling process from the given seed URL up to the specified depth.
     *
     * @param seedUrl  The initial URL to start crawling from.
     * @param maxDepth The maximum depth to crawl (number of levels to traverse).
     * @throws InterruptedException If the thread is interrupted while waiting for termination.
     */
    public void crawl(String seedUrl, int maxDepth) throws InterruptedException {
        // Submit the initial crawl task to the executor service
        executorService.submit(
                new CrawlTask(seedUrl, maxDepth, visitedUrls, executorService)
        );
        executorService.shutdown(); // Initiate shutdown after submitting tasks
        executorService.awaitTermination(5, TimeUnit.MINUTES); // Wait for all tasks to complete
    }

    /**
     * Retrieves the set of URLs that have been visited during the crawl.
     *
     * @return A set of visited URLs.
     */
    public Set<String> getVisitedUrls() {
        return visitedUrls;
    }
}