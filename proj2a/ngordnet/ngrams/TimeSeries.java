package ngordnet.ngrams;

import java.util.*;

/**
 * An object for mapping a year number (e.g. 1996) to numerical data. Provides
 * utility methods useful for data analysis.
 *
 * @author Josh Hug
 */
public class TimeSeries extends TreeMap<Integer, Double> {
    /**
     * Constructs a new empty TimeSeries.
     */
    public TimeSeries() {
        super();
    }

    /**
     * Creates a copy of TS, but only between STARTYEAR and ENDYEAR,
     * inclusive of both end points.
     */
    public TimeSeries(TimeSeries ts, int startYear, int endYear) {
        for (var year : ts.years()) {
            if (year >= startYear && year <= endYear) {
                put(year, ts.get(year));
            }
        }
    }

    /**
     * Returns all years for this TimeSeries (in any order).
     */
    public List<Integer> years() {
        return super.keySet().stream().toList();
    }

    /**
     * Returns all data for this TimeSeries (in any order).
     * Must be in the same order as years().
     */
    public List<Double> data() {
        return super.values().stream().toList();
    }

    /**
     * Returns the yearwise sum of this TimeSeries with the given TS. In other words, for
     * each year, sum the data from this TimeSeries with the data from TS. Should return a
     * new TimeSeries (does not modify this TimeSeries).
     */
    public TimeSeries plus(TimeSeries ts) {
        var newTs = new TimeSeries();
        for (var year : ts.years()) {
            newTs.put(year, ts.get(year));
        }
        for (var year : this.years()) {
            newTs.put(year, newTs.getOrDefault(year, 0.0) + this.get(year));
        }
        return newTs;
    }

    /**
     * Returns the quotient of the value for each year this TimeSeries divided by the
     * value for the same year in TS. If TS is missing a year that exists in this TimeSeries,
     * throw an IllegalArgumentException. If TS has a year that is not in this TimeSeries, ignore it.
     * Should return a new TimeSeries (does not modify this TimeSeries).
     */
    public TimeSeries dividedBy(TimeSeries ts) {
        var newTs = new TimeSeries();
        for (var year : this.years()) {
            if (!ts.containsKey(year)) {
                throw new IllegalArgumentException();
            }
            newTs.put(year, this.get(year) / ts.get(year));
        }
        return newTs;
    }
}
