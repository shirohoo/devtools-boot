package io.github.shirohoo.devtools.blog;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest
class BlogCompaniesApiTest {
    @Autowired
    BlogCompaniesRepository blogCompaniesRepository;

    WebTestClient webTestClient;

    @BeforeEach
    void setUp() {
        webTestClient = WebTestClient
            .bindToController(new BlogCompaniesApi(blogCompaniesRepository))
            .configureClient()
            .baseUrl("/api/v1/blogs/companies")
            .build();

        blogCompaniesRepository.saveAll(List.of(
            BlogCompanies.of("우아한형제들"),
            BlogCompanies.of("카카오")
        ));
    }

    @Test
    void findCategories() throws Exception {
        webTestClient.get()
            .exchange()
            .expectStatus().isOk()
            .expectBody().json("[\"우아한형제들\",\"카카오\"]");
    }
}
