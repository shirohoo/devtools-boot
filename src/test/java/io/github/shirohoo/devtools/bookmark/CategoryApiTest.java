package io.github.shirohoo.devtools.bookmark;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest
class CategoryApiTest {
    @Autowired
    CategoryRepository categoryRepository;

    WebTestClient webTestClient;

    @BeforeEach
    void setUp() {
        webTestClient = WebTestClient
            .bindToController(new CategoryApi(categoryRepository))
            .configureClient()
            .baseUrl("/api/v1/bookmarks/categories")
            .build();

        categoryRepository.saveAll(List.of(
            Category.of("JAVA"),
            Category.of("PS"),
            Category.of("SPRING")
        ));
    }

    @Test
    void findCategories() throws Exception {
        webTestClient.get()
            .exchange()
            .expectStatus().isOk()
            .expectBody().json("[\"JAVA\",\"PS\",\"SPRING\"]");
    }
}
