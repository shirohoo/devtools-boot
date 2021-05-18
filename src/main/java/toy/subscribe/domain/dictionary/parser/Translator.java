package toy.subscribe.domain.dictionary.parser;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import toy.subscribe.common.config.properties.ApiProperties;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class Translator {
    
    private final ApiProperties apiProperties;
    
    public Optional<String> translate(String enWord) {
        String baseURl = "https://dapi.kakao.com/";
        String uri = "v2/translation/translate";
        String srcLang = "en";
        String targetLang = "kr";
    
        WebClient.RequestHeadersSpec<?> header = WebClient.builder()
                                                          .baseUrl(baseURl)
                                                          .build()
                                                          .get()
                                                          .uri(uriBuilder->uriBuilder.path(uri)
                                                                                     .queryParam("src_lang", srcLang)
                                                                                     .queryParam("target_lang", targetLang)
                                                                                     .queryParam("query", enWord)
                                                                                     .build())
    
                                                          .header("Authorization", apiProperties.getKakaoKey());
    
        ReadObject readObject = null;
    
        try {
            Mono<String> stringMono = (Mono<String>) header
                    .retrieve()
                    .bodyToMono(String.class);
        
            ObjectMapper mapper = new ObjectMapper();
            readObject = mapper.readValue(stringMono.block(), ReadObject.class);
        }
        catch(JsonProcessingException e) {
            log.error(e.getMessage());
        }
        catch(Exception e) {
            log.error("KaKao API Error!");
        }
    
        String result = null;
        if(readObject != null) {
            result = String.valueOf(readObject.getTranslated_text()
                                              .get(0)).replaceAll("[\\[\\]]", "");
        }
        return Optional.of(result);
    }
    
    @Data
    private static class ReadObject implements Serializable {
        
        private List translated_text;
        
    }
    
}
