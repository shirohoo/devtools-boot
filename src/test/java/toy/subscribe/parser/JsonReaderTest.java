package toy.subscribe.parser;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.util.ResourceUtils;
import toy.subscribe.factory.appendices.Company;

import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class JsonReaderTest {
    
    @Test
    @DisplayName("JSON_List<String>_읽기")
    public void readStringList() throws Exception {
        //given
        File file = ResourceUtils.getFile("classpath:properties/propertiesFactory.json");
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
    
    @Test
    @DisplayName("JSON_List<Object>_읽기")
    public void readObjectList() throws Exception {
        //given
        File file = ResourceUtils.getFile("classpath:properties/propertiesFactory.json");
        FileReader reader = new FileReader(file);
        JSONParser parser = new JSONParser();
    
        //when
        JSONObject jsonObj = (JSONObject) parser.parse(reader);
        List<String> urls = (ArrayList<String>) jsonObj.get("urls");
        List<Company> companies = (ArrayList<Company>) jsonObj.get("companies");
    
        //then
        //RSS피드 주소의 개수와 RSS피드를 검증하는 Company객체의 개수는 같아야만 한다.
        assertThat(companies.size()).isEqualTo(urls.size());
    }
    
}