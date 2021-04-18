package toy.subscribe.factory;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import toy.subscribe.domain.RSSFeedMessage;
import toy.subscribe.domain.entity.FeedBoard;
import toy.subscribe.factory.appendices.Company;
import toy.subscribe.parser.JsonReader;
import toy.subscribe.repository.FeedBoardRepository;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class FeedBoardFactory {
    
    private final FeedBoardRepository feedBoardRepository;
    
    public FeedBoard findFeedBoardFrom(RSSFeedMessage message) {
        if(isNoDuplicate(message)) {
            String url = message.getLink();
            try {
                List<Company> companies = JsonReader.readCompanies();
                for(Company company : companies) {
                    if(url.contains(company.getKey())) {
                        message.setCompany(company.getName());
                        message.setImgPath(company.getImgPath());
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
