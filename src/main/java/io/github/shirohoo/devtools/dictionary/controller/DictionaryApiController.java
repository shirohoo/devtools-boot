package io.github.shirohoo.devtools.dictionary.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import io.github.shirohoo.devtools.config.exception.PageableLargeSizeException;
import io.github.shirohoo.devtools.dictionary.service.DictionaryProvideService;

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
        verifyPageable(pageable);
        return new ResponseEntity<>(dictionaryProvideService.provideDictionaryWrapper(pageable, enWord, krWord), HttpStatus.OK);
    }

    private void verifyPageable(final Pageable pageable) {
        if (pageable.getPageSize() > 200) {
            log.error("Request page size is too large !");
            throw new PageableLargeSizeException();
        }
    }
}
