package io.github.shirohoo.devtools.dictionary;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.shirohoo.devtools.config.external.ApiProperties;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClient.RequestHeadersSpec;
import reactor.core.publisher.Mono;

@Slf4j
@Component
@RequiredArgsConstructor
public class Translator {
    private final ObjectMapper objectMapper;
    private final ApiProperties apiProperties;

    /**
     * 영단어를 입력받아 카카오 번역 API에 넘긴다. 반환되는 단어는 한글이다.
     *
     * @return Optional-String
     */
    public Optional<String> translate(String enWord) {
        return Optional.of(getResult(getReadObject(exchangeKakaoTranslateApi(enWord))));
    }

    /**
     * 카카오 번역 API에 영단어를 넘긴다.
     *
     * @return RequestHeadersSpec
     */
    private RequestHeadersSpec<?> exchangeKakaoTranslateApi(final String enWord) {
        final String baseURl = "https://dapi.kakao.com/";
        final String uri = "v2/translation/translate";
        final String srcLang = "en";
        final String targetLang = "kr";
        return WebClient.builder()
            .baseUrl(baseURl)
            .build()
            .get()
            .uri(uriBuilder -> uriBuilder.path(uri)
                .queryParam("src_lang", srcLang)
                .queryParam("target_lang", targetLang)
                .queryParam("query", enWord)
                .build())

            .header("Authorization", apiProperties.getKakaoKey());
    }

    /**
     * 카카오 번역기 json 응답을 java 객체로 바인딩한다.
     *
     * @return ReadObject
     */
    private ReadObject getReadObject(final RequestHeadersSpec<?> header) {
        try {
            final Mono<String> stringMono = header.retrieve().bodyToMono(String.class);
            return objectMapper.readValue(stringMono.block(), ReadObject.class);
        } catch (JsonProcessingException e) {
            log.error(e.getMessage());
            return null;
        } catch (Exception e) {
            log.error("KaKao API error!");
            return null;
        }
    }

    /**
     * 반환된 단어에 문제가 없다면 반환하고, 문제가 있는 경우 null을 반환한다.
     *
     * @return String or NULL
     */
    private String getResult(final ReadObject readObject) {
        if (Objects.nonNull(readObject)) {
            return String.valueOf(readObject.getTranslated_text()
                .get(0)).replaceAll("[\\[\\]]", "");
        }
        return null;
    }

    /**
     * 카카오 번역기 API 응답을 받기 위한 내부 객체
     */
    @Data
    private static class ReadObject implements Serializable {
        private static final long serialVersionUID = 1874463342481741705L;
        private List translated_text; // 카카오 네임 컨벤션 동기화
    }
}
