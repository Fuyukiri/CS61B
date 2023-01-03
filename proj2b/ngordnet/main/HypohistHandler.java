package ngordnet.main;

import ngordnet.hugbrowsermagic.NgordnetQuery;
import ngordnet.hugbrowsermagic.NgordnetQueryHandler;
import ngordnet.ngrams.NGramMap;
import ngordnet.ngrams.TimeSeries;
import ngordnet.plotting.Plotter;
import org.knowm.xchart.XYChart;

import java.util.ArrayList;
import java.util.List;


public class HypohistHandler extends NgordnetQueryHandler {
    WordNet wn;
    NGramMap ngm;

    public HypohistHandler(NGramMap ngm, WordNet wn) {
        this.wn = wn;
        this.ngm = ngm;
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
        } else if (wordList.size() == 1) {
            ret = wn.getHyponym(wordList.get(0), k, startYear, endYear);
        } else {
            ret = wn.getHyponym(wordList, k, startYear, endYear);
        }

        ArrayList<TimeSeries> lts = new ArrayList<>();
        ArrayList<String> labels = new ArrayList<>();

        for (var word: ret) {
            var ts = ngm.countHistory(word, q.startYear(), q.endYear());
            if (ts.size() > 0) {
                lts.add(ts);
                labels.add(word);
            }
        }

        var chart = Plotter.generateTimeSeriesChart(labels, lts);
        var encodedImage = Plotter.encodeChartAsString(chart);

        return encodedImage;
    }
}
