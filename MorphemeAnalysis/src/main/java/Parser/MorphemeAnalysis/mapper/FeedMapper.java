package Parser.MorphemeAnalysis.mapper;

import Parser.MorphemeAnalysis.domain.DbFeed;
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

//    @Select("SELECT * FROM feed where category = ''")
    List<DbFeed> selectNotCategory();

    void updateCategory(@Param("link") String link, @Param("category") String category);

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
