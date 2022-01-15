package toy.subscribe.blog.parser;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.core.io.ClassPathResource;
import toy.subscribe.blog.model.Company;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.stream.Collectors;

import static java.util.stream.IntStream.range;

@Slf4j
public class JsonReader {
    @SuppressWarnings("unchecked")
    public static List<String> readUrls() {
        final JSONObject jsonObj = getJsonObject();
        if (Objects.nonNull(jsonObj)) {
            return (ArrayList<String>) jsonObj.get("urls");
        }
        return null;
    }

    public static List<Company> readCompanies() {
        final JSONArray jsonCompanies = (JSONArray) getJsonObject().get("companies");
        if (Objects.nonNull(jsonCompanies)) {
            return range(0, jsonCompanies.size()).mapToObj(getCompanyFrom(jsonCompanies))
                    .map(createCompanyEntity())
                    .collect(Collectors.toList());
        }
        return null;
    }

    private static IntFunction<JSONObject> getCompanyFrom(final JSONArray jsonCompanies) {
        return i -> (JSONObject) jsonCompanies.get(i);
    }

    private static Function<JSONObject, Company> createCompanyEntity() {
        return o -> Company.of(
                (String) o.get("key"),
                (String) o.get("name"),
                (String) o.get("imgPath"));
    }

    private static JSONObject getJsonObject() {
        try {
            return (JSONObject) new JSONParser().parse(new FileReader(readFileFromPropertiesUseInputStream()));
        }
        catch (IOException | NullPointerException o_O) {
            log.error("Read not json !");
            return null;
        }
        catch (ParseException e) {
            log.error("Json parsing error !");
            return null;
        }
    }

    private static File readFileFromPropertiesUseInputStream() throws IOException {
        try (InputStream inputStream = new ClassPathResource("properties/properties-factory.json").getInputStream()) {
            final File file = File.createTempFile("properties-factory", ".json");
            FileUtils.copyInputStreamToFile(inputStream, file);
            return file;
        }
    }
}
