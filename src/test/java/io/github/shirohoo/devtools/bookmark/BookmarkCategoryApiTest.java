package io.github.shirohoo.devtools.bookmark;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest
class BookmarkCategoryApiTest {
    @Autowired
    BookmarkCategoryRepository bookmarkCategoryRepository;

    WebTestClient webTestClient;

    @BeforeEach
    void setUp() {
        webTestClient = WebTestClient
            .bindToController(new BookmarkCategoryApi(bookmarkCategoryRepository))
            .configureClient()
            .baseUrl("/api/v1/bookmarks/categories")
            .build();

        bookmarkCategoryRepository.saveAll(List.of(
            BookmarkCategory.of("JAVA"),
            BookmarkCategory.of("PS"),
            BookmarkCategory.of("SPRING")
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
