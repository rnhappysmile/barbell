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

        for(DbFeed dbfeed : listFeed) {
            log.info(dbfeed.getMain_text());

            morpheme= analysis(dbfeed.getMain_text());
            log.info(dbfeed.getLink() + " : " + morpheme);
            feedMapper.updateCategory(dbfeed.getLink(), morpheme);
        }
    }

    private String analysis(String input) {
        try {
            Komoran komoran = new Komoran(DEFAULT_MODEL.FULL);
            KomoranResult analyzeResultList = komoran.analyze(input);

            long startTime = System.currentTimeMillis();
            List<Token> tokenList = analyzeResultList.getTokenList();
            long endTime = System.currentTimeMillis();
            StringBuilder result = new StringBuilder();

            log.info("time :" + (endTime - startTime));

            // 1. print each tokens by getTokenList()
//            log.info("==========print 'getTokenList()'==========");
//            for (Token token : tokenList) {
//            log.info(token);
//                log.info(token.getMorph() + "/" + token.getPos() + "(" + token.getBeginIndex() + "," + token.getEndIndex() + ")");
//            log.info();
//            }

            // 2. print nouns
            log.info("==========print 'getNouns()'==========");
            log.info(""+analyzeResultList.getNouns());
            log.info("");

            List<String> nouns = analyzeResultList.getNouns();
            HashMap<String, Integer> hm = new HashMap<>();
            for (String tag : nouns) {
                hm.put(tag, 1 + hm.getOrDefault(tag, 0));
            }

            // 3. print analyzed result as pos-tagged text
//            log.info("==========print 'getPlainText()'==========");
//            log.info(""+analyzeResultList.getPlainText());
//            log.info("");

            // 4. print analyzed result as list
//            log.info("==========print 'getList()'==========");
//            log.info(""+analyzeResultList.getList());
//            log.info("");

            // 5. print morphes with selected pos
//            log.info("==========print 'getMorphesByTags()'==========");
//            log.info(""+analyzeResultList.getMorphesByTags("NN", "NP", "NNG", "NNP", "NNB", "NR"));
//            List<String> morphesByTags = analyzeResultList.getMorphesByTags("NN", "NP", "NNG", "NNP", "NNB", "NR");

            List<String> keySetList = new ArrayList<>(hm.keySet());

            for (int i = 0; i < 3; i++) {
                log.info(keySetList.get(i));
            }

            Collections.sort(keySetList, (o1, o2) -> (hm.get(o2).compareTo(hm.get(o1))));

            // 근데 만약 ? 3개 이하면 어떻게 하지 ?

            int hmSize = hm.size();
            if(hmSize > 3) {
                hmSize = 3;
            }

            for (int i = 0; i < hmSize; i++) {
                log.info(keySetList.get(i));
                result.append(keySetList.get(i));

                if(i != hmSize - 1) {
                    result.append(":");
                }
            }
            // KeySetList에서 중복의 개수가 최대인 Top3만 리털 하게 만들어야 한다.
            // 혹은 KeySetList 전체를 리턴 해서, 리턴 받을 후 Top3만 확인 하도록 해야 한다.

            System.out.println("result.toString() : " + result.toString());

            return result.toString();
        } catch (Exception e) {
            log.info(""+e);
        }

        return "";
    }
}
