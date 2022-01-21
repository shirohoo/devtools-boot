package io.github.shirohoo.devtools.blog;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/blogs/companies")
class BlogCompaniesApi {
    private final BlogCompaniesRepository blogCompaniesRepository;

    @GetMapping
    List<String> findCategories() {
        return blogCompaniesRepository.findAll()
            .stream()
            .map(BlogCompanies::getCategory)
            .collect(Collectors.toUnmodifiableList());
    }
}
