package io.github.shirohoo.devtools.blog;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class BlogPostFactory {
    private final CompanyRepository companyRepository;
    private final BlogPostRepository blogPostRepository;

    BlogPost ifNonDuplicateConvert(RSSFeedMessage message) {
        if (isAlreadyExists(message)) {
            return null;
        }

        String url = message.getLink();
        for (Company company : companyRepository.findAll()) {
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

    private boolean isAlreadyExists(RSSFeedMessage message) {
        return blogPostRepository.existsByGuid(message.getGuid());
    }
}
