package toy.subscribe.domain.dictionary.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import toy.subscribe.domain.dictionary.model.Dictionary;
import toy.subscribe.domain.dictionary.parser.DocumentParser;
import toy.subscribe.domain.dictionary.parser.Translator;
import toy.subscribe.domain.dictionary.repository.DictionaryRepository;

import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class DictionaryServiceImpl implements DictionaryService {
    
    private final Translator translator;
    private final DictionaryRepository dictionaryRepository;
    
    private DocumentParser parser = new DocumentParser();
    
    private final String[] conlangFilter = {"taglib", "springframework", "namespace", "jackson", "springboot", "spring", "login",
                                            "username", "logback", "autoscaler", "hibernate", "kubernetes", "docker", "homebrew",
                                            "livereload", "jersey", "kotlin", "framework", "oauth", "testinstance", "junit4", "junit5",
                                            "webflux", "redis", "kafka", "elasticsearch", "log4j", "slf4j", "logstash", "kibana",
                                            "stackdriver", "timestamps", "timestamp", "nassert", "namespaces", "localhost", "initializer",
                                            "catalina", "hardcoding", "software", "jolokia", "param", "oracle", "apache", "github",
                                            "initializers", "exejar", "dockerfiles", "logstartupinfo", "rabbitmq", "testcontainers",
                                            "https", "mustache", "thymeleaf", "sigterm", "javax", "appname", "webapp", "fullstack",
                                            "backend", "frontend", "mappers", "formatters", "reloadtrigger", "autowire", "autowired",
                                            "kairos", "formatter", "mapper", "matcher", "atomikos", "buildpacks", "coroutines", "myapp",
                                            "setter", "getter", "lombok", "plugin", "gradle", "maven", "iframe", "matching", "cookies", "cookie",
                                            "session", "sessions", "proxies", "proxy", "cache", "mongodb", "infinispan", "tomcat", "netty",
                                            "mymodule", "pooled", "loggers", "logger", "devtools", "fasterxml", "humio", "facebook", "google",
                                            "mongo", "vanilla", "javascript", "jsonp", "jsonb", "cassandra", "dynatrace", "hikari", "header",
                                            "messaginghub", "myconfig", "servlets", "artemis", "nonheap", "whitelabel", "mytag", "servlet",
                                            "webjars", "petclinic", "mockito", "openjdk", "sidecar", "hamcrest", "dockerfile", "prodmq",
                                            "unmapper", "couchbase", "firebase", "groovy", "stackoverflow", "hazelcast", "liquibase",
                                            "layertools", "benmanes", "flywaydb", "antlib", "cloudfoundryapplication", "appengine", "wildfly",
                                            "rabbit", "prefixing", "populator", "keycloak", "sessionid", "reauthenticate", "mybank", "myopenid",
                                            "encodings", "decodings", "subdomains", "vicitim", "currval", "boolean", "arguments"};
    
    @Override
    @Transactional
    public void create(String htmlPath) {
        String html = parser.read(htmlPath);
        Set<String> enWords = parser.parsing(html);
        
        if(enWords == null) {
            log.warn("Could not make a dictionary! enWords is null.");
            return;
        }
        
        for(String enWord : enWords) {
            if(Arrays.stream(conlangFilter).anyMatch(enWord::equals)) {
                log.info("Filtered word : {}!", enWord);
                continue;
            }
    
            String krWord = translator.translate(enWord)
                                      .orElseThrow(()->new NoSuchElementException("단어를 찾지 못했습니다"));
    
            Dictionary dictionary = Dictionary.builder().enWord(enWord)
                                              .krWord(krWord)
                                              .build();
    
            if(dictionaryRepository.countByEnWord(enWord) == 0) {
                dictionaryRepository.save(dictionary);
            }
        }
    
    }
    
}
