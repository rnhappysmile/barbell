package Parser.RssAtom.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@Setter
@ToString
public class DbFeed {
    private String title;
    private String link;
    private String description;
    private String author;
    private String pub_date;
    private String category;
    private int view_count;
    private String main_text;
    private String image_url;
}
