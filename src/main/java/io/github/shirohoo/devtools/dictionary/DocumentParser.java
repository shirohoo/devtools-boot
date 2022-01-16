package io.github.shirohoo.devtools.dictionary;

import static java.util.Arrays.stream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;

@Slf4j
public class DocumentParser {
    private static final DocumentParser INSTANCE = new DocumentParser();
    private static final Pattern P_TAG_PATTERN = Pattern.compile("<p>.*</p>");
    private static final String NEW_LINE = System.lineSeparator();
    private static final String APACHE_OPENNLP_EN_MODEL = "/models/en-token.bin";
    private static final int WORD_LIMIT_LENGTH = 4;
    /**
     * 머신러닝으로 정제되지 않는 단어들을 정제하기 위한 수동 필터
     */
    private final String[] conlangFilter = {"taglib", "springframework", "namespace", "jackson", "springboot", "spring", "login", "winsw",
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
        "subdirectory", "proddb", "datasource", "bootapp", "application", "authn", "jayway", "eclipselink"};

    private DocumentParser() {}

    public static DocumentParser getInstance() {
        return INSTANCE;
    }

    /**
     * HTML PATH를 입력받아 해당 HTML에 접근하여 모든 문자열을 읽어 String 객체로 반환한다.
     *
     * @return String
     */
    public String read(String path) {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(path)))) {
            return br.lines()
                .map(read -> read + NEW_LINE)
                .collect(Collectors.joining());
        } catch (IOException e) {
            log.error(e.getMessage());
            return null;
        }
    }

    /**
     * BufferedReader로 읽은 HTML 문서에서 모든 HTML 태그를 제거한다. 이후 HTML이 제거된 문자열을 대해 머신러닝으로 학습된 자연어로 필터링한다. 필터링 된 자연어를 HashSet 객체로 변환하여 중복을 제거하고 반환한다.
     *
     * @return HashSet
     */
    public Set<String> parsing(String html) {
        return parsingNaturalLanguage(removeHtmlTags(html));
    }

    /**
     * 입력된 문자열이 4글자 이상의 자연어라면 HashSet 에 담는다.
     */
    private Set<String> parsingNaturalLanguage(final String charSequence) {
        try (InputStream inputStream = getClass().getResourceAsStream(APACHE_OPENNLP_EN_MODEL)) {
            final TokenizerModel model = new TokenizerModel(inputStream);
            final TokenizerME tokenizer = new TokenizerME(model);
            final String[] tokens = tokenizer.tokenize(charSequence);

            Set<String> set = new HashSet<>();
            for (String s1 : tokens) {
                if (s1.length() > WORD_LIMIT_LENGTH) {
                    set.add(s1);
                }
            }
            return set;
        } catch (IOException e) {
            log.error(e.getMessage());
            return null;
        }
    }

    /**
     * BufferedReader 로 읽은 HTML 문서에서 모든 HTML 태그를 제거하고 각 단어를 공백(" ")으로 구분하여 결합해 반환한다.
     *
     * @return String
     */
    private String removeHtmlTags(final String html) {
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
        return sb.toString().trim().toLowerCase()
            .replaceAll("[^a-zA-Z\\s\\.]", " ")
            .replaceAll(" +", " ");
    }

    /**
     * 단어가 카멜 케이스임에도 필터링 되지 않은 경우 참을 반환한다.
     *
     * @return boolean
     */
    private boolean isContainsWhiteSpace(final String refinedWord) {
        return refinedWord.contains(" ");
    }

    /**
     * 각 단어를 공백(" ")으로 구분하여 결합한다.
     */
    private void concat(final StringBuilder sb, final String refinedWord) {
        stream(refinedWord.split(" ")).forEach(s2 -> sb.append(s2).append(" "));
    }

    private String removeHtmlTags(final String html, final Matcher matcher) {
        String unrefinedWord = " " + html.substring(matcher.start(), matcher.end())
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
            .replaceAll("\\.com", "").replace(".", " ")
            .replaceAll("\\[[^\\[\\]]*\\]", "");
        return unrefinedWord;
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
            " ");
    }

    /**
     * 머신러닝으로 필터링 되지 않은 단어를 수동 필터로 필터링한다.
     *
     * @return HashSet
     */
    public Set<String> filtering(final Set<String> set) {
        return filteringForConlang(set);
    }

    private Set<String> filteringForConlang(final Set<String> set) {
        Set<String> result = new HashSet<>();
        for (String enWord : set) {
            if (enWord.endsWith("ing") | enWord.endsWith("es") | enWord.endsWith("s") | enWord.endsWith("ed")) {
                log.info("Filtered word : {}!", enWord);
                continue;
            }
            if (stream(conlangFilter).anyMatch(enWord::equals)) {
                log.info("Filtered word : {}!", enWord);
                continue;
            }
            result.add(enWord);
        }
        return result;
    }
}
