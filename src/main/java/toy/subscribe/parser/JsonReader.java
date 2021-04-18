package toy.subscribe.parser;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.util.ResourceUtils;
import toy.subscribe.factory.appendices.Company;

import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class JsonReader {
    
    public static List<String> readUrls() throws Exception {
        File file = ResourceUtils.getFile("classpath:properties/propertiesFactory.json");
        FileReader reader = new FileReader(file);
        JSONParser parser = new JSONParser();
        JSONObject jsonObj = (JSONObject) parser.parse(reader);
        
        return (ArrayList<String>) jsonObj.get("urls");
    }
    
    public static List<Company> readCompanies() throws Exception {
        File file = ResourceUtils.getFile("classpath:properties/propertiesFactory.json");
        FileReader reader = new FileReader(file);
        JSONParser parser = new JSONParser();
        
        JSONObject jsonObj = (JSONObject) parser.parse(reader);
        return (ArrayList<Company>) jsonObj.get("companies");
    }
    
}
