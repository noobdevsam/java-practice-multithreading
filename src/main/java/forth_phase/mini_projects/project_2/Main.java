package forth_phase.mini_projects.project_2;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        var crawler = new WebCrawler(4);
        var seed = "https://google.com"; // Replace with a valid seed URL
        var depth = 5;
        var start = System.currentTimeMillis();

        crawler.crawl(seed, depth);

        var end = System.currentTimeMillis();

        System.out.println("Crawled URLs: " + crawler.getVisitedUrls().size());
        System.out.println("Time taken: " + (end - start) + " ms");
    }

}
