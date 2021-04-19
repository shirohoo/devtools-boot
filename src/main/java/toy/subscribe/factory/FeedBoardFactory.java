package toy.subscribe.factory;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import toy.subscribe.domain.RSSFeedMessage;
import toy.subscribe.domain.entity.FeedBoard;
import toy.subscribe.factory.appendices.Company;
import toy.subscribe.repository.FeedBoardRepository;

import java.util.List;

import static toy.subscribe.parser.JsonReader.readCompanies;

@Slf4j
@Component
@RequiredArgsConstructor
public class FeedBoardFactory {
    
    private final FeedBoardRepository feedBoardRepository;
    
    public FeedBoard findFeedBoardFrom(RSSFeedMessage message) {
        if(isNoDuplicate(message)) {
            String url = message.getLink();
            try {
                List<Company> companies = readCompanies();
                for(int i = 0; i < companies.size(); i++) {
                    if(url.contains(companies.get(i).getKey())) {
                        message.setCompany(companies.get(i).getName());
                        message.setImgPath(companies.get(i).getImgPath());
                        break;
                    }
                }
            }
            catch(Exception e) {
                log.error("[Exception : JsonReader] {}", e.getMessage());
                return null;
            }
            
            return FeedBoard.builder()
                            .title(message.getTitle())
                            .company(message.getCompany())
                            .imgPath(message.getImgPath())
                            .guid(message.getGuid())
                            .build();
        }
        return null;
    }
    
    private boolean isNoDuplicate(RSSFeedMessage message) {
        return feedBoardRepository.countByGuid(message.getGuid().trim()) == 0L;
    }
    
}
