package toy.subscribe.parser;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class JsonReaderTest {
    
    @Test
    @DisplayName("JSON_List<String>_읽기")
    public void readStringList() throws Exception {
        //given
        FileReader reader = new FileReader(new ClassPathResource("static/properties/propertiesFactory.json").getFile());
        JSONParser parser = new JSONParser();
        
        //when
        JSONObject jsonObj = (JSONObject) parser.parse(reader);
        List<String> urls = (ArrayList<String>) jsonObj.get("urls");
        
        //then
        urls.forEach(it->assertThat(it).isNotBlank()
                                       .contains("feed")
                                       .startsWith("http"));
    }
    
    @Test
    @DisplayName("JSON_List<Object>_읽기")
    public void readObjectList() throws Exception {
        //given
        FileReader reader = new FileReader(new ClassPathResource("static/properties/propertiesFactory.json").getFile());
        JSONParser parser = new JSONParser();
    
        //when
        JSONObject jsonObj = (JSONObject) parser.parse(reader);
        List<String> urls = (ArrayList<String>) jsonObj.get("urls");
        JSONArray jsonCompanies = (JSONArray) jsonObj.get("companies");
    
        //then
        //RSS피드 주소의 개수와 RSS피드를 검증하는 Company객체의 개수는 같아야만 한다.
        assertThat(jsonCompanies.size()).isEqualTo(urls.size());
    
        for(int i = 0; i < jsonCompanies.size(); i++) {
            JSONObject o = (JSONObject) jsonCompanies.get(i);
            String key = (String) o.get("key");
            String name = (String) o.get("name");
            String imgPath = (String) o.get("imgPath");
        
            assertThat(key).isNotEmpty();
            assertThat(name).isNotEmpty();
            assertThat(imgPath).isNotEmpty();
        }
    }
    
}