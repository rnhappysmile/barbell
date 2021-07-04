package Parser.RssAtom.db.mapper;

import Parser.RssAtom.RSS.FeedMessage;
import Parser.RssAtom.domain.DbFeed;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface FeedMapper {

    @Select("SELECT * FROM feed Where #{col} = #{data}")
    DbFeed select(@Param("col") String col, @Param("data") String data);

    @Select("SELECT * FROM feed")
    List<DbFeed> findAll();

//    @Insert("INSERT INTO public.feed\n" +
//            "(title, link, description, author, pub_date, category, view_count, main_text, image_url)\n" +
//            "VALUES(#{title}::character varying, #{link}::character varying, '', #{author}::character varying, #{pubdate}, ''::character varying" +
//            ", 0, ''::text, ''::character varying);")
    @Insert("INSERT INTO public.feed\n" +
            "(title, link, description, author, pub_date, category, view_count, main_text, image_url)\n" +
            "VALUES(#{title}, #{link}, #{description}, #{author}, #{pub_date}, #{category}, #{view_count}, #{main_text}, #{image_url});")
    void insertFeed(DbFeed dbFeed);
}
//    title
//    description
//    link
//    author
//    guid
//    pubdate
