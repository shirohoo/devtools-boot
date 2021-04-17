package toy.subscribe.parser;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class UrlReaderTest {
    
    @Test
    @DisplayName("JSON_읽기")
    public void readJson() throws Exception {
        //given
        File file = ResourceUtils.getFile("classpath:properties/url.json");
        FileReader reader = new FileReader(file);
        JSONParser parser = new JSONParser();
        
        //when
        JSONObject jsonObj = (JSONObject) parser.parse(reader);
        List<String> urls = (ArrayList<String>) jsonObj.get("urls");
        
        //then
        urls.forEach(it->assertThat(it).isNotBlank()
                                       .contains("feed")
                                       .startsWith("http"));
    }
    
}