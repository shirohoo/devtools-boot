package io.github.shirohoo.devtools.dictionary;

import static java.util.Arrays.stream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;
import org.springframework.web.client.RestTemplate;

@Slf4j
class HtmlParser {
    private static final HtmlParser INSTANCE = new HtmlParser();

    private static final Pattern P_TAG_PATTERN = Pattern.compile("<p>.*</p>");

    /**
     * OpenNLP로 정제되지 않는 단어들을 정제하기 위한 수동 인공어 필터
     */
    private final Set<String> conlangFilter = Set.of(
        "taglib", "springframework", "namespace", "jackson", "springboot", "spring", "login", "winsw",
        "username", "logback", "autoscaler", "hibernate", "kubernetes", "docker", "homebrew", "datadog",
        "livereload", "jersey", "kotlin", "framework", "oauth", "testinstance", "junit4", "junit5", "bugfix",
        "webflux", "redis", "kafka", "elasticsearch", "log4j", "slf4j", "logstash", "kibana", "hsqldb",
        "stackdriver", "timestamps", "timestamp", "nassert", "namespaces", "localhost", "initializer",
        "catalina", "hardcoding", "software", "jolokia", "param", "oracle", "apache", "github", "querydsl",
        "initializers", "exejar", "dockerfiles", "logstartupinfo", "rabbitmq", "testcontainers", "filesystem",
        "https", "mustache", "thymeleaf", "sigterm", "javax", "appname", "webapp", "fullstack", "websocket",
        "backend", "frontend", "mappers", "formatters", "reloadtrigger", "autowire", "kairos", "clinic",
        "formatter", "mapper", "matcher", "atomikos", "buildpacks", "coroutines", "myapp", "logout", "wikipedia",
        "setter", "getter", "lombok", "plugin", "gradle", "maven", "iframe", "matching", "cookies", "cookie",
        "session", "sessions", "proxies", "proxy", "cache", "mongodb", "infinispan", "tomcat", "netty",
        "mymodule", "pooled", "loggers", "logger", "devtools", "fasterxml", "humio", "facebook", "google",
        "mongo", "vanilla", "javascript", "jsonp", "jsonb", "cassandra", "dynatrace", "hikari", "header",
        "messaginghub", "myconfig", "servlets", "artemis", "nonheap", "whitelabel", "mytag", "servlet", "hashi",
        "webjars", "petclinic", "mockito", "openjdk", "sidecar", "hamcrest", "dockerfile", "prodmq", "millisecond",
        "unmapper", "couchbase", "firebase", "groovy", "stackoverflow", "hazelcast", "liquibase", "micrometer",
        "layertools", "benmanes", "flywaydb", "antlib", "cloudfoundryapplication", "appengine", "wildfly",
        "rabbit", "prefixing", "populator", "keycloak", "sessionid", "reauthenticate", "mybank", "myopenid",
        "encodings", "decodings", "subdomains", "vicitim", "currval", "boolean", "arguments", "charset",
        "subdirectory", "proddb", "datasource", "bootapp", "application", "authn", "jayway", "eclipselink"
    );

    private static final String APACHE_OPENNLP_EN_MODEL = "/models/en-token.bin";

    private static final String SPACE_CHAR = " ";

    private static final int WORD_LIMIT_LENGTH = 4;

    private HtmlParser() {}

    static HtmlParser getInstance() {
        return INSTANCE;
    }

    /**
     * HTML PATH를 입력받아 해당 HTML에 접근하여 모든 문자열을 읽어 String 객체로 반환한다.
     *
     * @return String
     */
    String read(String url) {
        return new RestTemplate()
            .getForObject(url, String.class);
    }

    /**
     * BufferedReader로 읽은 HTML 문서에서 모든 HTML 태그를 제거한다. 이후 HTML이 제거된 문자열을 대해 머신러닝으로 학습된 자연어로 필터링한다. 필터링 된 자연어를 HashSet 객체로 변환하여 중복을 제거하고 반환한다.
     *
     * @return HashSet
     */
    Set<String> parse(String html) {
        String refinedHtml = removeHtmlTags(html);
        Set<String> filteredNaturalLanguage = parsingNaturalLanguage(refinedHtml);
        return filteringConstructedLanguage(filteredNaturalLanguage);
    }

    /**
     * 입력된 문자열이 4글자 이상의 자연어라면 HashSet 에 담는다.
     */
    private Set<String> parsingNaturalLanguage(String charSequence) {
        try (InputStream inputStream = getClass().getResourceAsStream(APACHE_OPENNLP_EN_MODEL)) {
            assert inputStream != null;
            TokenizerModel model = new TokenizerModel(inputStream);
            TokenizerME tokenizer = new TokenizerME(model);
            String[] tokens = tokenizer.tokenize(charSequence);

            return stream(tokens)
                .filter(word -> word.length() > WORD_LIMIT_LENGTH)
                .collect(Collectors.toSet());
        } catch (IOException e) {
            log.error(e.getMessage());
            return Set.of();
        }
    }

    /**
     * BufferedReader 로 읽은 HTML 문서에서 모든 HTML 태그를 제거하고 각 단어를 공백(" ")으로 구분하여 결합해 반환한다.
     *
     * @return String
     */
    private String removeHtmlTags(String html) {
        StringBuilder sb = new StringBuilder();
        Matcher matcher = P_TAG_PATTERN.matcher(html);
        while (matcher.find()) {
            String string = removeHtmlTags(html, matcher);
            String refinedWord = splitCamelCase(string);
            if (isContainsWhiteSpace(refinedWord)) {
                concat(sb, refinedWord);
                continue;
            }
            sb.append(string);
        }
        return sb.toString()
            .trim()
            .toLowerCase()
            .replaceAll("[^a-zA-Z\\s\\.]", SPACE_CHAR)
            .replaceAll(" +", SPACE_CHAR);
    }

    /**
     * 단어가 카멜 케이스임에도 필터링 되지 않은 경우 참을 반환한다.
     *
     * @return boolean
     */
    private boolean isContainsWhiteSpace(String refinedWord) {
        return refinedWord.contains(SPACE_CHAR);
    }

    /**
     * 각 단어를 공백(" ")으로 구분하여 결합한다.
     */
    private void concat(StringBuilder sb, String refinedWord) {
        sb.append(
            stream(refinedWord.split(SPACE_CHAR))
                .map(string -> string + SPACE_CHAR)
                .collect(Collectors.joining())
        );
    }

    private String removeHtmlTags(String html, Matcher matcher) {
        return SPACE_CHAR + html.substring(matcher.start(), matcher.end())
            .replaceAll("<b>", "").replaceAll("</b>", "")
            .replaceAll("<a [^<>]*>", "").replaceAll("</a>", "")
            .replaceAll("<p>", "").replaceAll("</p>", "")
            .replaceAll("<sup [^<>]*>", "").replaceAll("</sup>", "")
            .replaceAll("<span [^<>]*>", "").replaceAll("</span>", "")
            .replaceAll("<i [^<>]*>>", "").replaceAll("</i>", "")
            .replaceAll("<table [^<>]*>>", "").replaceAll("</table>", "")
            .replaceAll("<block [^<>]*>>", "").replaceAll("</block>", "")
            .replaceAll("<ul [^<>]*>>", "").replaceAll("</ul>", "")
            .replaceAll("<li [^<>]*>>", "").replaceAll("</li>", "")
            .replaceAll("<div [^<>]*>>", "").replaceAll("</div>", "")
            .replaceAll("<h [^<>]*>>", "").replaceAll("</h>", "")
            .replaceAll("www\\.", "").replaceAll("http", "")
            .replaceAll("\\.com", "").replace(".", SPACE_CHAR)
            .replaceAll("\\[[^\\[\\]]*\\]", "");
    }

    /**
     * 카멜 케이스로 구성된 단어를 분리하여 의미있는 단어로 변환한다.
     * <pre>
     *
     *     예: anyMatch -> any, match
     *
     * </pre>
     *
     * @return String
     */
    private String splitCamelCase(String word) {
        return word.replaceAll(String.format("%s|%s|%s",
                "(?<=[A-Z])(?=[A-Z][a-z])",
                "(?<=[^A-Z])(?=[A-Z])",
                "(?<=[A-Za-z])(?=[^A-Za-z])"),
            SPACE_CHAR);
    }

    /**
     * OpenNLP로 필터링 되지 않은 단어를 인공어 필터로 수동 필터링한다.
     *
     * @return HashSet
     */
    private Set<String> filteringConstructedLanguage(Set<String> words) {
        return words.stream()
            .filter(word -> !word.endsWith("ing") && !word.endsWith("es") && !word.endsWith("s") && !word.endsWith("ed"))
            .filter(word -> !conlangFilter.contains(word))
            .collect(Collectors.toSet());
    }
}
