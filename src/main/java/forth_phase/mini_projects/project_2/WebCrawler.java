package forth_phase.mini_projects.project_2;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

class WebCrawler {
    private final ExecutorService executorService;
    private final Set<String> visitedUrls = ConcurrentHashMap.newKeySet();

    public WebCrawler(int maxThreads) {
        this.executorService = Executors.newFixedThreadPool(maxThreads);
    }

    private void crawl(String seedUrl, int maxDepth) throws InterruptedException {
        executorService.submit(
                new CrawlTask(seedUrl, maxDepth, visitedUrls, executorService)
        );
        executorService.shutdown();
        executorService.awaitTermination(5, TimeUnit.MINUTES);
    }

    public Set<String> getVisitedUrls() {
        return visitedUrls;
    }
}
