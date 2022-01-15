package toy.subscribe.blog.parser;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import toy.subscribe.blog.model.Company;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class JsonReaderTest {
    @Test
    @DisplayName("JSON 프로퍼티에서 URL 리스트를 읽어온다")
    public void readUrls() throws Exception {
        // given
        List<String> urls = JsonReader.readUrls();

        // when, then
        urls.forEach(it -> assertThat(it).isNotBlank()
                                         .contains("feed")
                                         .startsWith("http"));
    }

    @Test
    @DisplayName("JSON 프로퍼티에서 회사정보 리스트를 읽어온다")
    public void readCompanies() throws Exception {
        // given
        List<Company> companies = JsonReader.readCompanies();

        // when, then
        assertThat(companies).isNotNull();
        companies.forEach(company -> {
            assertThat(company.getKey()).isNotEmpty();
            assertThat(company.getName()).isNotEmpty();
            assertThat(company.getImgPath()).isNotEmpty();
        });
    }
}
