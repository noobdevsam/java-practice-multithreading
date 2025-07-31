package forth_phase.mini_projects.project_2;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.logging.Logger;

class CrawlTask implements Runnable {

    private final String url;
    private final int depth;
    private final Set<String> urlsVisited;
    private final ExecutorService executor;
    private final Logger logger = Logger.getLogger(CrawlTask.class.getName());


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
     * Runs this operation.
     */
    @Override
    public void run() {

        if (depth == 0 || urlsVisited.contains(url)) {
            return; // Base case: stop if depth is 0 or URL has already been visited
        }
        urlsVisited.add(url); // Mark this URL as visited

        logger.info("[" + Thread.currentThread().getName() + "] visiting: " + url);

        try {
            Document doc = Jsoup
                    .connect(url)
                    .timeout(3000)
                    .get();
            Elements links = doc.select("a[href]");

            for (var link : links) {
                String absoluteUrl = link.absUrl("href");

                if (!absoluteUrl.isEmpty() && urlsVisited.add(absoluteUrl)) {
                    executor.submit(
                            new CrawlTask(absoluteUrl, depth - 1, urlsVisited, executor)
                    );
                }
            }
        } catch (Exception e) {
            logger.severe("Error fetching: " + url + " - " + e.getMessage());
        }
    }
}
