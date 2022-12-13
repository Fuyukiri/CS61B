package ngordnet.ngrams;

import edu.princeton.cs.algs4.In;

import java.util.*;

/**
 * An object that provides utility methods for making queries on the
 * Google NGrams dataset (or a subset thereof).
 * <p>
 * An NGramMap stores pertinent data from a "words file" and a "counts
 * file". It is not a map in the strict sense, but it does provide additional
 * functionality.
 *
 * @author Josh Hug
 */
public class NGramMap {
    /**
     * Constructs an NGramMap from WORDSFILENAME and COUNTSFILENAME.
     */
    private final Map<String, TimeSeries> wordTimeSeriesMap;
    private final TimeSeries countMap;

    public NGramMap(String wordsFilename, String countsFilename) {
        wordTimeSeriesMap = new HashMap<>();
        var in = new In(wordsFilename);
        while (in.hasNextLine()) {
            String[] line = in.readLine().split("\\s+");
            var name = line[0];
            var year = Integer.valueOf(line[1]);
            var times = Double.valueOf(line[2]);
            var ts = wordTimeSeriesMap.computeIfAbsent(name, k -> new TimeSeries());
            ts.put(year, times);
        }

        countMap = new TimeSeries();
        in = new In(countsFilename);
        while (in.hasNextLine()) {
            String[] line = in.readLine().split(",");
            countMap.put(Integer.valueOf(line[0]), Double.valueOf(line[1]));
        }
    }

    /**
     * Provides the history of WORD. The returned TimeSeries should be a copy,
     * not a link to this NGramMap's TimeSeries. In other words, changes made
     * to the object returned by this function should not also affect the
     * NGramMap. This is also known as a "defensive copy".
     */
    public TimeSeries countHistory(String word) {
        var ts = wordTimeSeriesMap.get(word);
        var startYear = ts.firstKey();
        var endYear = ts.lastKey();
        return countHistory(word, startYear, endYear);
    }

    /**
     * Provides the history of WORD between STARTYEAR and ENDYEAR, inclusive of both ends. The
     * returned TimeSeries should be a copy, not a link to this NGramMap's TimeSeries. In other words,
     * changes made to the object returned by this function should not also affect the
     * NGramMap. This is also known as a "defensive copy".
     */
    public TimeSeries countHistory(String word, int startYear, int endYear) {
        return new TimeSeries(wordTimeSeriesMap.get(word), startYear, endYear);
    }

    /**
     * Returns a defensive copy of the total number of words recorded per year in all volumes.
     */
    public TimeSeries totalCountHistory() {
        var ts = new TimeSeries();
        ts.putAll(countMap);
        return ts;
    }

    /**
     * Provides a TimeSeries containing the relative frequency per year of WORD compared to
     * all words recorded in that year.
     */
    public TimeSeries weightHistory(String word) {
        return wordTimeSeriesMap.get(word).dividedBy(countMap);
    }

    /**
     * Provides a TimeSeries containing the relative frequency per year of WORD between STARTYEAR
     * and ENDYEAR, inclusive of both ends.
     */
    public TimeSeries weightHistory(String word, int startYear, int endYear) {
        return new TimeSeries(wordTimeSeriesMap.get(word), startYear, endYear).dividedBy(countMap);
    }

    /**
     * Returns the summed relative frequency per year of all words in WORDS.
     */
    public TimeSeries summedWeightHistory(Collection<String> words) {
        var ts = new TimeSeries();
        for (var word : words) {
            ts = ts.plus(weightHistory(word));
        }
        return ts;
    }

    /**
     * Provides the summed relative frequency per year of all words in WORDS
     * between STARTYEAR and ENDYEAR, inclusive of both ends. If a word does not exist in
     * this time frame, ignore it rather than throwing an exception.
     */
    public TimeSeries summedWeightHistory(Collection<String> words,
                                          int startYear, int endYear) {
        var ts = summedWeightHistory(words);
        return new TimeSeries(ts, startYear, endYear);
    }

}
