package io.github.shirohoo.devtools.dictionary;

import io.github.shirohoo.devtools.ContentProvider;
import io.github.shirohoo.devtools.common.DevtoolsResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
class DictionaryApi {
    private final ContentProvider<Dictionary> dictionaryProvider;

    @GetMapping("/dictionaries")
    ResponseEntity<DevtoolsResponse<DictionaryDto>> findPages(
        Pageable pageable,
        @RequestParam(value = "enWord", required = false) String enWord,
        @RequestParam(value = "krWord", required = false) String krWord
    ) {
        return new ResponseEntity<>(
            DevtoolsResponse.<DictionaryDto>builder()
                .pages(dictionaryProvider.provide(pageable, enWord, krWord).map(Dictionary::toDto))
                .visitorsOfDay(dictionaryProvider.visitorsOfDay())
                .visitorsOfTotal(dictionaryProvider.visitorsOfTotal())
                .build(),
            HttpStatus.OK);
    }
}
