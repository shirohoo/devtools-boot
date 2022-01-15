package io.github.shirohoo.devtools.blog;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class BlogPostFactory {
    private final BlogPostRepository feedBoardRepository;

    BlogPost ifNonDuplicateConvert(RSSFeedMessage message) {
        if (!isNonDuplicate(message)) {
            return null;
        }

        List<Company> companies = JsonReader.readCompanies();

        if (companies == null) {
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

        return BlogPost.builder()
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
