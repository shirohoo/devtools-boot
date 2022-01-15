package io.github.shirohoo.devtools.blog;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.XMLEvent;
import lombok.extern.slf4j.Slf4j;

@Slf4j
class RSSFeedParser {
    private static final String TITLE = "title";
    private static final String LANGUAGE = "language";
    private static final String COPYRIGHT = "copyright";
    private static final String LINK = "link";
    private static final String AUTHOR = "author";
    private static final String ITEM = "item";
    private static final String GUID = "guid";
    private static final String PUBDATE = "pubdate";

    private final URL url;

    private RSSFeedParser(String feedUrl) throws MalformedURLException {
        url = getUrl(feedUrl);
    }

    static RSSFeedParser from(String feedUrl) throws MalformedURLException {
        return new RSSFeedParser(feedUrl);
    }

    private URL getUrl(String feedUrl) throws MalformedURLException {
        try {
            return new URL(feedUrl);
        } catch (MalformedURLException e) {
            throw new MalformedURLException("url is malformed. either no legal protocol could be found in a specification string or the string could not be parsed");
        }
    }

    RSSFeed readFeed() throws XMLStreamException {
        RSSFeed feed = null;
        try {
            boolean isFeedHeader = true;

            String title = "";
            String link = "";
            String language = "";
            String copyright = "";
            String author = "";
            String pubdate = "";
            String guid = "";

            XMLInputFactory inputFactory = XMLInputFactory.newInstance();

            try (InputStream inputStream = openInputStream()) {

                XMLEventReader eventReader = inputFactory.createXMLEventReader(inputStream);

                while (eventReader.hasNext()) {
                    XMLEvent event = eventReader.nextEvent();

                    if (event.isStartElement()) {
                        String localPart = event.asStartElement().getName().getLocalPart();

                        switch (localPart) {

                            case ITEM:
                                if (isFeedHeader) {
                                    isFeedHeader = false;
                                    feed = RSSFeed.of(title, link, language, copyright, pubdate);
                                }
                                event = eventReader.nextEvent();
                                break;

                            case TITLE:

                                title = getCharacterData(event, eventReader);
                                break;
                            case LINK:

                                link = getCharacterData(event, eventReader);
                                break;

                            case GUID:
                                guid = getCharacterData(event, eventReader);
                                break;

                            case LANGUAGE:
                                language = getCharacterData(event, eventReader);
                                break;

                            case AUTHOR:
                                author = getCharacterData(event, eventReader);
                                break;

                            case PUBDATE:
                                pubdate = getCharacterData(event, eventReader);
                                break;

                            case COPYRIGHT:
                                copyright = getCharacterData(event, eventReader);
                                break;
                        }
                    } else if (event.isEndElement()) {
                        if (event.asEndElement().getName().getLocalPart() == ITEM) {
                            RSSFeedMessage message = RSSFeedMessage.builder()
                                .author(author)
                                .guid(guid)
                                .link(link)
                                .title(title)
                                .pubDate(pubdate)
                                .build();

                            feed.getMessages().add(message);

                            event = eventReader.nextEvent();
                            continue;
                        }
                    }
                }
                eventReader.close();
            }
        } catch (XMLStreamException | IOException o_O) {
            throw new XMLStreamException(o_O.getMessage());
        }
        return feed;
    }

    private InputStream openInputStream() throws IOException {
        try {
            return url.openStream();
        } catch (IOException e) {
            throw new IOException(e.getMessage());
        }
    }

    private String getCharacterData(XMLEvent event, XMLEventReader eventReader) throws XMLStreamException {
        String s = "";
        event = eventReader.nextEvent();

        if (event instanceof Characters) {
            s = event.asCharacters().getData();
        }

        return s;
    }
}


