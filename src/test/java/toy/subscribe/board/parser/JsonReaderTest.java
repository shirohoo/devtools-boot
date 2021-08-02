package toy.subscribe.board.parser;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import toy.subscribe.board.model.Company;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class JsonReaderTest {
    @Test
    @DisplayName("JSON_프로퍼티에서_URL_리스트를_읽어온다")
    public void readUrls() throws Exception {
        // given
        List<String> urls = JsonReader.readUrls();

        // then
        urls.forEach(it->assertThat(it).isNotBlank()
                                       .contains("feed")
                                       .startsWith("http"));
    }

    @Test
    @DisplayName("JSON_프로퍼티에서_회사정보_리스트를_읽어온다")
    public void readCompanies() throws Exception {
        // given
        List<Company> companies = JsonReader.readCompanies();

        // then
        assertThat(companies).isNotNull();
        companies.forEach(company->{
            assertThat(company.getKey()).isNotEmpty();
            assertThat(company.getName()).isNotEmpty();
            assertThat(company.getImgPath()).isNotEmpty();
        });
    }
}
