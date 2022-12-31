package ngordnet.main;

import ngordnet.hugbrowsermagic.NgordnetQuery;
import ngordnet.hugbrowsermagic.NgordnetQueryHandler;


public class HyponymsHandler extends NgordnetQueryHandler {
    WordNet wn;
    public HyponymsHandler(WordNet wn) {
        this.wn = wn;
    }
    @Override
    public String handle(NgordnetQuery q) {
        String word = q.words().get(0);
//        List<Integer> idList = wn.getIdList(word);
        var ret = wn.getHyponym(word);
        return ret.toString();
    }
}
