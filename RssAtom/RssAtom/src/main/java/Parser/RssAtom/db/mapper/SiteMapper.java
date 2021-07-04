package Parser.RssAtom.db.mapper;

import Parser.RssAtom.domain.DbSite;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SiteMapper {

    @Select("SELECT * FROM site Where feed_type = 'RSS'")
    List<DbSite> findRSSAll();

    @Select("SELECT * FROM site")
    List<DbSite> findAll();
}
