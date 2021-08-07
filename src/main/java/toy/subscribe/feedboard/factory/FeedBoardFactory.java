package toy.subscribe.feedboard.factory;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import toy.subscribe.feedboard.model.Company;
import toy.subscribe.feedboard.model.FeedBoard;
import toy.subscribe.feedboard.model.RSSFeedMessage;
import toy.subscribe.feedboard.parser.JsonReader;
import toy.subscribe.feedboard.repository.FeedBoardRepository;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class FeedBoardFactory {
    private final FeedBoardRepository feedBoardRepository;

    public FeedBoard getFeedBoard(RSSFeedMessage message) {
        if(isNonDuplicate(message)) {
            String url = message.getLink();
            List<Company> companies = JsonReader.readCompanies();

            if(companies != null) {
                int size = companies.size();
                for(int i = 0; i < size; i++) {
                    if(url.contains(companies.get(i).getKey())) {
                        message.setCompany(companies.get(i).getName());
                        message.setImgPath(companies.get(i).getImgPath());
                        break;
                    }
                }

                return FeedBoard.builder()
                                .title(message.getTitle())
                                .company(message.getCompany())
                                .imgPath(message.getImgPath())
                                .guid(message.getGuid())
                                .build();
            }
        }
        return null;
    }

    private boolean isNonDuplicate(RSSFeedMessage message) {
        return !feedBoardRepository.existsByGuid(message.getGuid());
    }
}
