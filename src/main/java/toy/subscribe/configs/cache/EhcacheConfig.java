package toy.subscribe.configs.cache;

import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.ExpiryPolicyBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.config.units.MemoryUnit;
import org.ehcache.jsr107.Eh107Configuration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import toy.subscribe.feedboard.model.FeedBoard;
import toy.subscribe.feedboard.model.RSSFeedMessage;

import javax.cache.CacheManager;
import javax.cache.Caching;
import javax.cache.spi.CachingProvider;
import java.time.Duration;

@Configuration
@EnableCaching
public class EhcacheConfig {
    public static final String RSS_CACHE = "rss-cache";

    @Bean
    public CacheManager ehCacheManager() {
        final CachingProvider provider = Caching.getCachingProvider();
        final CacheManager cacheManager = provider.getCacheManager();
        cacheManager.createCache(RSS_CACHE, Eh107Configuration.fromEhcacheCacheConfiguration(
                CacheConfigurationBuilder.newCacheConfigurationBuilder(
                                RSSFeedMessage.class,
                                FeedBoard.class,
                                ResourcePoolsBuilder.newResourcePoolsBuilder().offheap(10, MemoryUnit.MB))
                        .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(600)))
        ));
        return cacheManager;

    }
}
