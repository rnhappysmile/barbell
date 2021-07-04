package Parser.RssAtom.RSS;

import java.util.ArrayList;
import java.util.List;

public class Feed {
    /**
     * 타이틀
     */
    final String title;

    /**
     * 링크 url
     */
    final String link;

    /**
     * 설명
     */
    final String description;

    /**
     * 언어
     */
    final String language;

    /**
     *  저작권정보
     */
    final String copyright;

    /**
     * 일자
     */
    final String pubDate;

    final List<FeedMessage> entries = new ArrayList<FeedMessage>();

    public Feed(String title, String link, String description, String language,
                String copyright, String pubDate) {
        this.title = title;
        this.link = link;
        this.description = description;
        this.language = language;
        this.copyright = copyright;
        this.pubDate = pubDate;
    }

    public List<FeedMessage> getMessages() {
        return entries;
    }

    public String getTitle() {
        return title;
    }

    public String getLink() {
        return link;
    }

    public String getDescription() {
        return description;
    }

    public String getLanguage() {
        return language;
    }

    public String getCopyright() {
        return copyright;
    }

    public String getPubDate() {
        return pubDate;
    }

    @Override
    public String toString() {
        return "Feed [copyright=" + copyright + ", description=" + description
                + ", language=" + language + ", link=" + link + ", pubDate="
                + pubDate + ", title=" + title + "]";
    }

}