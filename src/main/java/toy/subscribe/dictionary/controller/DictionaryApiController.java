package toy.subscribe.dictionary.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import toy.subscribe.dictionary.service.DictionaryProvideService;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class DictionaryApiController {
    private final DictionaryProvideService dictionaryProvideService;

    @GetMapping("/dictionaries")
    public ResponseEntity<?> receiveDictionariesRequest(Pageable pageable,
                                                        @RequestParam(value = "enWord", required = false) String enWord,
                                                        @RequestParam(value = "krWord", required = false) String krWord) {
        if(pageable.getPageSize() > 200) {
            log.error("Request page size is too large !");
            return new ResponseEntity<>("Request page size is too large !", HttpStatus.BAD_REQUEST);
        }
        else {
            if(StringUtils.isEmpty(enWord)) {
                enWord = "";
            }
            if(StringUtils.isEmpty(krWord)) {
                krWord = "";
            }
            return new ResponseEntity<>(dictionaryProvideService.provideDictionaryWrapper(pageable, enWord, krWord), HttpStatus.OK);
        }
    }
}
