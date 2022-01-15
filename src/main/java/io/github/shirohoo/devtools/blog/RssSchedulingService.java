package io.github.shirohoo.devtools.blog;

import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.toList;
import java.net.MalformedURLException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;
import javax.xml.stream.XMLStreamException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class RssSchedulingService {
    private final BlogPostFactory blogPostFactory;
    private final CompanyRepository companyRepository;
    private final BlogPostRepository blogPostRepository;

    @Transactional
    public void collect() {
        List<String> urls = companyRepository.findAllUrl();
        blogPostRepository.saveAll(urls.stream()
                .map(this::getRssFeeds)
                .filter(Objects::nonNull)
                .flatMap(this::getMessageStreams)
                .map(blogPostFactory::ifNonDuplicateConvert)
                .filter(Objects::nonNull)
                .collect(toList()))
            .forEach(this::loggingNewFeeds);
    }

    private RSSFeed getRssFeeds(String url) {
        return readRssFeed(requireNonNull(createRssFeedParser(url)));
    }

    private RSSFeed readRssFeed(RSSFeedParser parser) {
        try {
            return parser.readFeed();
        } catch (XMLStreamException e) {
            log.error("XML parsing error. url={}", parser.getUrl());
        }
        return null;
    }

    private RSSFeedParser createRssFeedParser(String url) {
        try {
            return RSSFeedParser.from(url);
        } catch (MalformedURLException e) {
            log.error("Syntax not found : 'http' or 'https'.");
        }
        return null;
    }

    private Stream<? extends RSSFeedMessage> getMessageStreams(RSSFeed rssFeed) {
        return rssFeed.getMessages().stream();
    }

    private void loggingNewFeeds(BlogPost blogPost) {
        log.info("New post={}", blogPost.toString());
    }
}
