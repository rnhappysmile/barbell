package Parser.MorphemeAnalysis;

import Parser.MorphemeAnalysis.Service.Analysis;
import Parser.MorphemeAnalysis.mapper.FeedMapper;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("Parser.MorphemeAnalysis.mapper")
@Slf4j
public class MorphemeAnalysisApplication implements CommandLineRunner {

	@Autowired
	FeedMapper feedMapper;

	public static void main(String[] args) {
		SpringApplication.run(MorphemeAnalysisApplication.class, args);
	}

	@Override
	@SuppressWarnings("squid:S106")
	public void run(String... args) throws Exception {
		log.info("start MorphemeAnalysisApplication");
		Analysis analysis = new Analysis(feedMapper);
		analysis.run();
		log.info("end MorphemeAnalysisApplication");
	}

}
