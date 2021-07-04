package Parser.RssAtom;

import Parser.RssAtom.Service.Parser;
import Parser.RssAtom.db.mapper.FeedMapper;
import Parser.RssAtom.db.mapper.SiteMapper;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("Parser.RssAtom.db.mapper")
@Slf4j
public class RssAtomApplication implements CommandLineRunner {

	@Autowired
	FeedMapper feedMapper;

	@Autowired
	SiteMapper siteMapper;

	public static void main(String[] args)

	{
		log.error("this is main");
		SpringApplication.run(RssAtomApplication.class, args);
	}

	@Override
	@SuppressWarnings("squid:S106")
	public void run(String... args) throws Exception {
		Parser parser = new Parser(feedMapper, siteMapper);

		log.trace("This is TRACE Log!");
		log.debug("This is DEBUG Log!");
		log.info("This is INFO Log!");
		log.warn("This is WARN Log!");
		log.error("This is ERROR Log!");

//		parser.parsing();
	}

}
