package toy.subscribe.domain.dictionary.parser;

import lombok.extern.slf4j.Slf4j;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;

import java.io.*;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class DocumentParser {
    
    private final String[] conlangFilter = {"taglib", "springframework", "namespace", "jackson", "springboot", "spring", "login", "winsw",
                                            "username", "logback", "autoscaler", "hibernate", "kubernetes", "docker", "homebrew", "datadog",
                                            "livereload", "jersey", "kotlin", "framework", "oauth", "testinstance", "junit4", "junit5",
                                            "webflux", "redis", "kafka", "elasticsearch", "log4j", "slf4j", "logstash", "kibana", "hsqldb",
                                            "stackdriver", "timestamps", "timestamp", "nassert", "namespaces", "localhost", "initializer",
                                            "catalina", "hardcoding", "software", "jolokia", "param", "oracle", "apache", "github",
                                            "initializers", "exejar", "dockerfiles", "logstartupinfo", "rabbitmq", "testcontainers", "filesystem",
                                            "https", "mustache", "thymeleaf", "sigterm", "javax", "appname", "webapp", "fullstack", "websocket",
                                            "backend", "frontend", "mappers", "formatters", "reloadtrigger", "autowire", "kairos", "clinic",
                                            "formatter", "mapper", "matcher", "atomikos", "buildpacks", "coroutines", "myapp", "logout",
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
                                            "subdirectory", "proddb", "datasource", "bootapp", "application", "authn"};
    
    public String read(String path) {
        StringBuilder sb = new StringBuilder();
        try(BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(path)))) {
            String read;
            while((read = br.readLine()) != null) {
                sb.append(read).append("\n");
            }
        }
        catch(IOException e) {
            log.error(e.getMessage());
        }
        return sb.toString();
    }
    
    public Set<String> parsing(String html) {
        StringBuilder sb = new StringBuilder();
        Pattern pattern = Pattern.compile("<p>.*</p>");
        Matcher matcher = pattern.matcher(html);
    
        while(matcher.find()) {
            String s = " " + html.substring(matcher.start(), matcher.end())
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
            
            String s1 = splitCamelCase(s);
            if(s1.contains(" ")) {
                String[] strings1 = s1.split(" ");
                for(String s2 : strings1) {
                    sb.append(s2).append(" ");
                }
            }
            else {
                sb.append(s);
            }
        }
    
        String s = sb.toString().trim().toLowerCase()
                     .replaceAll("[^a-zA-Z\\s\\.]", " ")
                     .replaceAll(" +", " ");
    
        Set<String> set = null;
        try(InputStream inputStream = getClass()
                .getResourceAsStream("/models/en-token.bin")) {
            TokenizerModel model = new TokenizerModel(inputStream);
            TokenizerME tokenizer = new TokenizerME(model);
            String[] tokens = tokenizer.tokenize(s);
    
            set = new HashSet<>();
            for(String s1 : tokens) {
                if(s1.length() > 4) {
                    set.add(s1);
                }
            }
        }
        catch(IOException e) {
            log.error(e.getMessage());
        }
    
        return set;
    }
    
    public Set<String> filtering(Set<String> set) {
        Set<String> result = new HashSet<>();
        for(String enWord : set) {
            if(enWord.endsWith("ing") | enWord.endsWith("es") | enWord.endsWith("s") | enWord.endsWith("ed")) {
                log.info("Filtered word : {}!", enWord);
                continue;
            }
            if(Arrays.stream(conlangFilter).anyMatch(enWord::equals)) {
                log.info("Filtered word : {}!", enWord);
                continue;
            }
            result.add(enWord);
        }
        return result;
    }
    
    private String splitCamelCase(String s) {
        return s.replaceAll(String.format("%s|%s|%s",
                                          "(?<=[A-Z])(?=[A-Z][a-z])",
                                          "(?<=[^A-Z])(?=[A-Z])",
                                          "(?<=[A-Za-z])(?=[^A-Za-z])"),
                            " ");
    }
    
}
