package Parser.RssAtom.Service;

import Parser.RssAtom.RSS.Feed;
import Parser.RssAtom.RSS.FeedMessage;
import Parser.RssAtom.RSS.RSSFeedParser;
import Parser.RssAtom.db.mapper.FeedMapper;
import Parser.RssAtom.db.mapper.SiteMapper;
import Parser.RssAtom.domain.DbFeed;
import Parser.RssAtom.domain.DbSite;
import com.rometools.rome.feed.synd.SyndContent;
import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;


import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@Slf4j
public class Parser {

    @Autowired
    FeedMapper feedMapper;

    @Autowired
    SiteMapper siteMapper;

    public Parser(FeedMapper feedMapper, SiteMapper siteMapper) {
        this.feedMapper = feedMapper;
        this.siteMapper = siteMapper;
    }

    public void run() throws ParseException {
        LocalDate localDate = LocalDate.now();
        DateFormat formatter = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz", Locale.ENGLISH);
        SimpleDateFormat feedDateFormat = new SimpleDateFormat("yyyy-MM-dd");

        int test = 0;
        int maxLinkLength = 0;
        int minLinkLength = 65534;
        int maxDescriptionLength = 0;
        int minDescriptionLength = 65534;

//        Site에서 주소를 가져온다.
//        Site 주소의 List를 확인하고 각 Site의 RSS Feed를 가져온다.
        List<DbSite> dbSiteList = siteMapper.findRSSAll();
        if (!dbSiteList.isEmpty()) {
            for (DbSite dbSite : dbSiteList) {
                log.info(dbSite.getLink());
//                RSSFeedParser parser = new RSSFeedParser(
//                        dbSite.getLink());
                RSSFeedParser parser = new RSSFeedParser(
                        "https://medium.com/feed/coupang-tech");
                Feed feed = parser.readFeed();
                List<FeedMessage> feedMessageList = feed.getMessages();

                for (FeedMessage feedMessage : feedMessageList) {
                    if (null != feedMessage.getPubdate() && !feedMessage.getPubdate().equals("")) {
//                        System.out.println(feedMessage.getPubdate());
                        Date parseDate = formatter.parse(feedMessage.getPubdate());
                        String parseDateText = feedDateFormat.format(parseDate);

//                        if (localDate.equals(parseDateText)) {
//                        System.out.println(dbSite.getLink());

//                            feedMapper.insertFeed(feedMessage);
                        System.out.println("feedMessage.getPubdate().length() : " + feedMessage.getPubdate().length());
                        System.out.println("feedMessage.getAuthor().length() : " + feedMessage.getAuthor().length());
                        System.out.println("feedMessage.getLink().length() : " + feedMessage.getLink().length());
                        System.out.println("feedMessage.getDescription().length() : " + feedMessage.getDescription().length());
                        System.out.println("feedMessage.getGuid().length() : " + feedMessage.getGuid().length());
                        System.out.println("feedMessage.getTitle().length() : " + feedMessage.getTitle().length());
//                            System.out.println("feedMessage.getContent().length() : " +feedMessage.getContent().length());
                        System.out.println("feedMessage.toString() : " + feedMessage.toString());

                        if (feedMessage.getLink().length() > maxLinkLength)
                            maxLinkLength = feedMessage.getLink().length();
                        if (feedMessage.getLink().length() < minLinkLength)
                            minLinkLength = feedMessage.getLink().length();
                        if (feedMessage.getDescription().length() > maxDescriptionLength)
                            maxDescriptionLength = feedMessage.getDescription().length();
                        if (feedMessage.getDescription().length() < minDescriptionLength)
                            minDescriptionLength = feedMessage.getDescription().length();
//                        }
                    }
                }
            }
        }

        System.out.println("maxLinkLength : " + maxLinkLength);
        System.out.println("minLinkLength : " + minLinkLength);
        System.out.println("maxDescriptionLength : " + maxDescriptionLength);
        System.out.println("minDescriptionLength : " + minDescriptionLength);

    }

    public void parsing() {
//        String urlString = "https://d2.naver.com/d2.atom";
        boolean ok = false;

        LocalDate localDate = LocalDate.now();
        DateFormat formatter = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH);
        SimpleDateFormat feedDateFormat = new SimpleDateFormat("yyyy-MM-dd");

        List<DbSite> dbSiteList = siteMapper.findRSSAll();
        if (!dbSiteList.isEmpty()) {
            for (DbSite dbSite : dbSiteList) {
                System.out.println(dbSite.getTitle());
                try {
                    URL feedUrl = new URL(dbSite.getLink());
                    SyndFeed syndFeed = new SyndFeedInput().build(new XmlReader(feedUrl));

                    List<SyndEntry> entries = syndFeed.getEntries();
                    for (SyndEntry entry : entries) {
                        Date parseDate = formatter.parse(entry.getPublishedDate().toString());
                        String parseDateText = feedDateFormat.format(parseDate);


//                        System.out.println("getPublishedDate : " + entry.getPublishedDate());
//                        System.out.println("parseDateText : " + parseDateText);
                        if (localDate.equals(parseDateText)) {
                            System.out.println("getTItle : " + entry.getTitle());

                            DbFeed dbFeed = new DbFeed(
                                    entry.getTitle(),
                                    entry.getLink(),
                                    "",
                                    entry.getAuthor(),
                                    parseDateText,
                                    "",
                                    0,
                                    "",
                                    ""
                            );

                            if (null != entry.getDescription() && null != entry.getDescription().getValue())
                                dbFeed.setMain_text(entry.getDescription().getValue());

                            if (entry.getContents() != null) {
                                for (SyndContent it : entry.getContents()) {
                                    SyndContent syndContent = it;

                                    if (syndContent != null) {
                                        String value = syndContent.getValue();
                                        dbFeed.setMain_text(syndContent.getValue());
                                    }
                                }
                            }

                            log.info(dbFeed.toString());
                            feedMapper.insertFeed(dbFeed);
                        }
                    }


                } catch (Exception ex) {
                    ex.printStackTrace();
                    log.error("ERROR: " + ex.getMessage());
                }

                System.out.println();
            }
        }

    }
}
