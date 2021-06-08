package toy.subscribe.domain.board.parser;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.core.io.ClassPathResource;
import toy.subscribe.domain.board.model.Company;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class JsonReader {
    public static List<String> readUrls() {
        JSONObject jsonObj = null;
        try {
            FileReader reader = new FileReader(readFileFromPropertiesUseInputStream());
            JSONParser parser = new JSONParser();
            jsonObj = (JSONObject) parser.parse(reader);
        }
        catch(IOException | NullPointerException o_O) {
            log.error("Read not json !");
            return null;
        }
        catch(ParseException e) {
            log.error("Json parsing error !");
            return null;
        }
        if(jsonObj != null) {
            return (ArrayList<String>) jsonObj.get("urls");
        }
        else {
            return null;
        }
    }
    
    public static List<Company> readCompanies() {
        List<Company> companies = null;
        JSONArray jsonCompanies = null;
        try {
            FileReader reader = new FileReader(readFileFromPropertiesUseInputStream());
            JSONParser parser = new JSONParser();
            companies = new ArrayList<>();
            JSONObject jsonObj = (JSONObject) parser.parse(reader);
            jsonCompanies = (JSONArray) jsonObj.get("companies");
        }
        catch(IOException | NullPointerException e) {
            log.error("Read not json !");
            return null;
        }
        catch(ParseException e) {
            log.error("Json parsing error !");
            return null;
        }
        if(jsonCompanies != null) {
            for(int i = 0; i < jsonCompanies.size(); i++) {
                JSONObject o = (JSONObject) jsonCompanies.get(i);
                companies.add(new Company(
                                      (String) o.get("key"),
                                      (String) o.get("name"),
                                      (String) o.get("imgPath"))
                             );
            }
            return companies;
        }
        else {
            return null;
        }
    }
    
    private static File readFileFromPropertiesUseInputStream() throws IOException {
        File file;
        try(InputStream inputStream = new ClassPathResource("properties/properties-factory.json").getInputStream()) {
            file = File.createTempFile("properties-factory", ".json");
            FileUtils.copyInputStreamToFile(inputStream, file);
        }
        return file;
    }
    
}
