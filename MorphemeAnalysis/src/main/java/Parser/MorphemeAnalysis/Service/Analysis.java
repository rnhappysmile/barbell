package Parser.MorphemeAnalysis.Service;

import Parser.MorphemeAnalysis.domain.DbFeed;
import Parser.MorphemeAnalysis.mapper.FeedMapper;
import kr.co.shineware.nlp.komoran.constant.DEFAULT_MODEL;
import kr.co.shineware.nlp.komoran.core.Komoran;
import kr.co.shineware.nlp.komoran.model.KomoranResult;
import kr.co.shineware.nlp.komoran.model.Token;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

@Slf4j
public class Analysis {
    @Autowired
    FeedMapper feedMapper;

    public Analysis(FeedMapper feedMapper) {
        this.feedMapper = feedMapper;
    }

    public void run() {
        String morpheme = "";

        List<DbFeed> listFeed = feedMapper.selectNotCategory();

//        for(DbFeed dbfeed : listFeed) {
        log.info(listFeed.get(2).getMain_text());

        morpheme= analysis(listFeed.get(2).getMain_text());
        feedMapper.updateCategory(listFeed.get(0).getLink(), morpheme);

//        }
    }

    private String analysis(String input) {
        try {
            Komoran komoran = new Komoran(DEFAULT_MODEL.FULL);
            KomoranResult analyzeResultList = komoran.analyze(input);

            long startTime = System.currentTimeMillis();
            List<Token> tokenList = analyzeResultList.getTokenList();
            long endTime = System.currentTimeMillis();

            log.info("time :" + (endTime - startTime));

            // 1. print each tokens by getTokenList()
            log.info("==========print 'getTokenList()'==========");
            for (Token token : tokenList) {
//            log.info(token);
                log.info(token.getMorph() + "/" + token.getPos() + "(" + token.getBeginIndex() + "," + token.getEndIndex() + ")");
//            log.info();
            }

            // 2. print nouns
            log.info("==========print 'getNouns()'==========");
            log.info(""+analyzeResultList.getNouns());
            log.info("");

            // 3. print analyzed result as pos-tagged text
            log.info("==========print 'getPlainText()'==========");
            log.info(""+analyzeResultList.getPlainText());
            log.info("");

            // 4. print analyzed result as list
            log.info("==========print 'getList()'==========");
            log.info(""+analyzeResultList.getList());
            log.info("");

            // 5. print morphes with selected pos
            log.info("==========print 'getMorphesByTags()'==========");
            log.info(""+analyzeResultList.getMorphesByTags("NN", "NP", "NNG", "NNP", "NNB", "NR"));
            List<String> morphesByTags = analyzeResultList.getMorphesByTags("NN", "NP", "NNG", "NNP", "NNB", "NR");
            HashMap<String, Integer> hm = new HashMap<>();
            for (String tag : morphesByTags) {
                hm.put(tag, 1 + hm.getOrDefault(tag, 0));
            }

            List<String> keySetList = new ArrayList<>(hm.keySet());

            Collections.sort(keySetList, (o1, o2) -> (hm.get(o2).compareTo(hm.get(o1))));

            for (int i = 0; i < 3; i++) {
                log.info(keySetList.get(i));
            }

            return keySetList.get(0);
        } catch (Exception e) {
            log.info(""+e);
        }

        return "";
    }
}
