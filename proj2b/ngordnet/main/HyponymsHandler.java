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

//        var wordList = word.split(",");
        List<String> ret;
        if (wordList.size() == 1) {
            ret = wn.getHyponym(wordList.get(0));
        } else {
            ret = wn.getHyponym(wordList);
        }

        return ret.toString();
    }
}
