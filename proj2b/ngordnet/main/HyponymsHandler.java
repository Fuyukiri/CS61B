package ngordnet.main;

import ngordnet.hugbrowsermagic.NgordnetQuery;
import ngordnet.hugbrowsermagic.NgordnetQueryHandler;

import java.util.List;


public class HyponymsHandler extends NgordnetQueryHandler {
    WordNet wn;
    public HyponymsHandler(WordNet wn) {
        this.wn = wn;
    }
    @Override
    public String handle(NgordnetQuery q) {
        var wordList = q.words();
        var k = q.k();
        var startYear = q.startYear();
        var endYear = q.endYear();
        List<String> ret;

        if (wordList.size() == 1 && k == 0) {
            ret = wn.getHyponym(wordList.get(0));
        } else if (wordList.size() > 1 && k == 0) {
            ret = wn.getHyponym(wordList);
        } else if (wordList.size() == 1 && k != 0) {
            ret = wn.getHyponym(wordList.get(0), k, startYear, endYear);
        } else {
            ret = wn.getHyponym(wordList, k, startYear, endYear);
        }

        return ret.toString();
    }
}
