package Parser.MorphemeAnalysis.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@Setter
@ToString
public class DbSite {
    private String title;
    private String link;
    private String description;
    private String language;
    private String pub_date;
    private String generator;
    private String managing_editor;
    private String feed_type;
    private String profile_image;
    private Boolean company_feed;
}
