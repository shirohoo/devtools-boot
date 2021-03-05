package toy.feed.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import toy.feed.domain.FeedBoard;
import toy.feed.object.*;
import toy.feed.repository.FeedBoardRepository;

@Service
@RequiredArgsConstructor
public class CollectPostService {
    
    private final FeedBoardRepository feedBoardRepository;
    
    public void getAll () throws Exception {
        getWoowabros();
        getToss();
        getDailyHotel();
        getDaangnMarket();
        getKakao();
        getYanolja();
    }
    
    private void getWoowabros () throws Exception {
        RSSFeedParser parser = new RSSFeedParser(
                "https://woowabros.github.io/feed.xml"
        );
        
        RSSFeed feed = parser.readFeed();
        
        for (RSSFeedMessage message : feed.getMessages()) {
            FeedBoard feedBoard = toFeedBoard(message);
            if (feedBoard != null) {
                feedBoardRepository.save(feedBoard);
            }
        }
    }
    
    private void getToss () throws Exception {
        RSSFeedParser parser = new RSSFeedParser(
                "https://blog.toss.im/feed/"
        );
        
        RSSFeed feed = parser.readFeed();
        
        for (RSSFeedMessage message : feed.getMessages()) {
            FeedBoard feedBoard = toFeedBoard(message);
            if (feedBoard != null) {
                feedBoardRepository.save(feedBoard);
            }
        }
        
    }
    
    private void getDailyHotel () throws Exception {
        RSSFeedParser parser = new RSSFeedParser(
                "https://dailyhotel.io/feed"
        );
        
        RSSFeed feed = parser.readFeed();
        
        for (RSSFeedMessage message : feed.getMessages()) {
            FeedBoard feedBoard = toFeedBoard(message);
            if (feedBoard != null) {
                feedBoardRepository.save(feedBoard);
            }
        }
        
    }
    
    private void getDaangnMarket () throws Exception {
        RSSFeedParser parser = new RSSFeedParser(
                "https://medium.com/feed/daangn"
        );
        
        RSSFeed feed = parser.readFeed();
        
        for (RSSFeedMessage message : feed.getMessages()) {
            FeedBoard feedBoard = toFeedBoard(message);
            if (feedBoard != null) {
                feedBoardRepository.save(feedBoard);
            }
        }
        
    }
    
    private void getKakao () throws Exception {
        RSSFeedParser parser = new RSSFeedParser(
                "https://tech.kakao.com/feed/"
        );
        
        RSSFeed feed = parser.readFeed();
        
        for (RSSFeedMessage message : feed.getMessages()) {
            FeedBoard feedBoard = toFeedBoard(message);
            if (feedBoard != null) {
                feedBoardRepository.save(feedBoard);
            }
        }
        
    }
    
    private void getYanolja () throws Exception {
        RSSFeedParser parser = new RSSFeedParser(
                "https://yanolja.github.io/feed.xml"
        );
        
        RSSFeed feed = parser.readFeed();
        
        for (RSSFeedMessage message : feed.getMessages()) {
            FeedBoard feedBoard = toFeedBoard(message);
            if (feedBoard != null) {
                feedBoardRepository.save(feedBoard);
            }
        }
        
    }
    
    private FeedBoard toFeedBoard ( RSSFeedMessage message ) {
        if (isNoDuplicate(message)) {
            String url = message.getLink();
            if (url.contains("woowabros")) {
                message.setDescription("우아한형제들");
            }
            else if (url.contains("toss")) {
                message.setDescription("토스");
            }
            else if (url.contains("dailyhotel")) {
                message.setDescription("데일리호텔");
            }
            else if (url.contains("daangn")) {
                message.setDescription("당근마켓");
            }
            else if (url.contains("kakao")) {
                message.setDescription("카카오");
            }
            else if (url.contains("yanolja")) {
                message.setDescription("야놀자");
            }
            return FeedBoard.builder()
                            .title(message.getTitle())
                            .company(message.getDescription())
                            .guid(message.getGuid())
                            .build();
        }
        return null;
    }
    
    private boolean isNoDuplicate ( RSSFeedMessage message ) {
        return feedBoardRepository.countByGuid(message.getGuid().trim()) == 0L;
    }
    
}
