package toy.subscribe.feedboard.factory;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import toy.subscribe.feedboard.model.Company;
import toy.subscribe.feedboard.model.FeedBoard;
import toy.subscribe.feedboard.model.RSSFeedMessage;
import toy.subscribe.feedboard.parser.JsonReader;
import toy.subscribe.feedboard.repository.FeedBoardRepository;

import java.util.List;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class FeedBoardFactory {
    private final FeedBoardRepository feedBoardRepository;

    public FeedBoard ifNonDuplicateConvert(RSSFeedMessage message) {
        if (!isNonDuplicate(message)) {
            return null;
        }

        List<Company> companies = JsonReader.readCompanies();

        if (Objects.isNull(companies)) {
            return null;
        }

        String url = message.getLink();

        for (Company company : companies) {
            if (url.contains(company.getKey())) {
                message.setCompany(company.getName());
                message.setImgPath(company.getImgPath());
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

    private boolean isNonDuplicate(RSSFeedMessage message) {
        return !feedBoardRepository.existsByGuid(message.getGuid());
    }
}
