package ngordnet.main;

import ngordnet.hugbrowsermagic.NgordnetQuery;
import ngordnet.hugbrowsermagic.NgordnetQueryHandler;
import ngordnet.ngrams.NGramMap;

public class HistoryTextHandler extends NgordnetQueryHandler {

    private final NGramMap ngm;
    HistoryTextHandler(NGramMap ngm) {
        this.ngm = ngm;
    }

    @Override
    public String handle(NgordnetQuery q) {
        StringBuilder sb = new StringBuilder();
        for (var word : q.words()) {
            sb.append(word);
            sb.append(": ");
            sb.append(ngm.weightHistory(word, q.startYear(), q.endYear()).toString());
            sb.append("\n");
        }
        return sb.toString();
    }
}
