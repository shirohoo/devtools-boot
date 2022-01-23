package io.github.shirohoo.devtools.dictionary;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.Serializable;
import java.util.List;
import java.util.Optional;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Slf4j
@Component
@RequiredArgsConstructor
class Translator {
    private final ObjectMapper objectMapper;
    private final KakaoProperties kakaoProperties;

    /**
     * 영단어를 입력받아 카카오 번역 API에 넘긴다. 반환되는 단어는 한글이다.
     *
     * @return Optional-String
     */
    Optional<String> translate(String enWord) {
        String translated = exchangeKakaoTranslateApi(enWord);
        ReadObject readObject = getReadObject(translated);
        return getResult(readObject);
    }

    /**
     * 카카오 번역 API에 동기식으로 영단어를 넘기고 번역된 한글 단어를 반환받는다.
     *
     * @return RequestHeadersSpec
     */
    private String exchangeKakaoTranslateApi(String enWord) {
        return WebClient.builder()
            .baseUrl("https://dapi.kakao.com/")
            .build()
            .get()
            .uri(uriBuilder -> uriBuilder.path("v2/translation/translate")
                .queryParam("src_lang", "en")
                .queryParam("target_lang", "kr")
                .queryParam("query", enWord)
                .build())
            .header("Authorization", kakaoProperties.getKakaoKey())
            .retrieve()
            .bodyToMono(String.class)
            .block();
    }

    private ReadObject getReadObject(String translated) {
        ReadObject readObject = null;
        try {
            readObject = objectMapper.readValue(translated, ReadObject.class);
        } catch (JsonProcessingException e) {
            log.error(e.getMessage());
        }
        return readObject;
    }

    private Optional<String> getResult(ReadObject readObject) {
        if (readObject != null) {
            String krWord = readObject.getTranslated_text().get(0).get(0); // 카카오는 [[개요]] 와 같이 2차원 배열로 응답해줌
            return Optional.of(krWord);
        }
        return Optional.empty();
    }

    /**
     * 카카오 번역기 API 응답을 받기 위한 내부 객체
     */
    @Data
    private static class ReadObject implements Serializable {
        private static final long serialVersionUID = 1874463342481741705L;
        private List<List<String>> translated_text; // 카카오 응답스펙 동기화
    }
}
