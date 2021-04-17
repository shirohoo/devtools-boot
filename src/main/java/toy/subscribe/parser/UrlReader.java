package toy.subscribe.parser;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UrlReader {
    
    public List<String> read() throws IOException, ParseException {
        File file = ResourceUtils.getFile("classpath:properties/url.json");
        FileReader reader = new FileReader(file);
        JSONParser parser = new JSONParser();
        JSONObject jsonObj = (JSONObject) parser.parse(reader);
        
        return (ArrayList<String>) jsonObj.get("urls");
    }
    
}
