package io.github.shirohoo.devtools.blog;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import io.github.shirohoo.devtools.config.http.log.repository.HttpLogRepository;
import io.github.shirohoo.devtools.config.http.wrapper.HttpResponseWrapper;

@Service
@RequiredArgsConstructor
class BlogPostProvideService {
    private final BlogPostRepository feedBoardRepository;
    private final HttpLogRepository requestLogRepository;

    @Transactional(readOnly = true)
    HttpResponseWrapper<BlogPostDto> provideFeedBoardWrapper(Pageable pageable, String company, String title) {
        return HttpResponseWrapper.<BlogPostDto>builder()
            .pages(feedBoardRepository.findPage(pageable, company, title))
            .visitorsOfDay(requestLogRepository.findDau())
            .visitorsOfReduce(requestLogRepository.findCumulativeVisitors())
            .build();
    }
}
