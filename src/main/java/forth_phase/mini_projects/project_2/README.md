# Parallel Web Crawler

---

## Objective:

Crawl web pages in parallel starting from a seed URL,
extract all links, and crawl them recursively up to a certain depth.

## Concepts Practiced:

- Recursive concurrent tasks
- URL de-duplication (shared set)
- Thread pool with bounded recursion
- HTML parsing with `jsoup`

## Dependency:

- `jsoup` for HTML parsing

Add the following dependency to your `pom.xml`:

```xml

<dependency>
    <groupId>org.jsoup</groupId>
    <artifactId>jsoup</artifactId>
    <version>1.21.1</version>
</dependency>
```