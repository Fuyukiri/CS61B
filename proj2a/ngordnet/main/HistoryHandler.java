package ngordnet.main;

import ngordnet.hugbrowsermagic.NgordnetQuery;
import ngordnet.hugbrowsermagic.NgordnetQueryHandler;
import ngordnet.ngrams.NGramMap;
import ngordnet.ngrams.TimeSeries;
import ngordnet.plotting.Plotter;
import org.knowm.xchart.XYChart;

import java.util.ArrayList;

public class HistoryHandler extends NgordnetQueryHandler {
    private final NGramMap ngm;
    public HistoryHandler(NGramMap ngm) {
        this.ngm = ngm;
    }
    @Override
    public String handle(NgordnetQuery q) {

        ArrayList<TimeSeries> lts = new ArrayList<>();
        ArrayList<String> labels = new ArrayList<>(q.words());

        for (var word: q.words()) {
            var ts = ngm.countHistory(word, q.startYear(), q.endYear());
            lts.add(ts);
        }

        XYChart chart = Plotter.generateTimeSeriesChart(labels, lts);
        String encodedImage = Plotter.encodeChartAsString(chart);

        return encodedImage;

    }
}
