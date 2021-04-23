package toy.subscribe.parser;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.core.io.ClassPathResource;
import toy.subscribe.factory.appendices.Company;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class JsonReader {
    
    public static List<String> readUrls() throws Exception {
        FileReader reader = new FileReader(createInputStreamToFile());
        JSONParser parser = new JSONParser();
        JSONObject jsonObj = (JSONObject) parser.parse(reader);
        
        return (ArrayList<String>) jsonObj.get("urls");
    }
    
    public static List<Company> readCompanies() throws Exception {
        FileReader reader = new FileReader(createInputStreamToFile());
        JSONParser parser = new JSONParser();
    
        List<Company> companies = new ArrayList<>();
    
        JSONObject jsonObj = (JSONObject) parser.parse(reader);
        JSONArray jsonCompanies = (JSONArray) jsonObj.get("companies");
    
        for(int i = 0; i < jsonCompanies.size(); i++) {
            JSONObject o = (JSONObject) jsonCompanies.get(i);
            String key = (String) o.get("key");
            String name = (String) o.get("name");
            String imgPath = (String) o.get("imgPath");
            companies.add(new Company(key, name, imgPath));
        }
    
        return companies;
    }
    
    private static File createInputStreamToFile() throws IOException {
        InputStream inputStream = new ClassPathResource("static/properties/propertiesFactory.json").getInputStream();
        File file = File.createTempFile("propertiesFactory", ".json");
        try {
            FileUtils.copyInputStreamToFile(inputStream, file);
        }
        finally {
            IOUtils.closeQuietly(inputStream);
        }
        return file;
    }
    
}
