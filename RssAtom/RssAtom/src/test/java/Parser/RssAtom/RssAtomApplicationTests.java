package Parser.RssAtom;

import Parser.RssAtom.db.mapper.FeedMapper;
import Parser.RssAtom.db.mapper.SiteMapper;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class RssAtomApplicationTests {

	@Autowired
	FeedMapper feedMapper;

	SiteMapper siteMapper;

	@Test
	public void SiteFindAll() {
		System.out.println("Site:" + siteMapper.findAll());
	}

}
