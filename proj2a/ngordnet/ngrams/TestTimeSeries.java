package ngordnet.ngrams;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Unit Tests for the TimeSeries class.
 *
 * @author Josh Hug
 */
public class TestTimeSeries {
    TimeSeries catPopulation;
    TimeSeries dogPopulation;

    @Before
    public void setup() {
        catPopulation = new TimeSeries();
        catPopulation.put(1991, 0.0);
        catPopulation.put(1992, 100.0);
        catPopulation.put(1994, 200.0);

        dogPopulation = new TimeSeries();
        dogPopulation.put(1994, 400.0);
        dogPopulation.put(1995, 500.0);
    }


    @Test
    public void testFromSpec() {
        TimeSeries totalPopulation = catPopulation.plus(dogPopulation);
        // expected: 1991: 0,
        //           1992: 100
        //           1994: 600
        //           1995: 500

        List<Integer> expectedYears = new ArrayList<>
                (Arrays.asList(1991, 1992, 1994, 1995));

        assertEquals(expectedYears, totalPopulation.years());

        List<Double> expectedTotal = new ArrayList<>
                (Arrays.asList(0.0, 100.0, 600.0, 500.0));

        for (int i = 0; i < expectedTotal.size(); i += 1) {
            assertEquals(expectedTotal.get(i), totalPopulation.data().get(i), 1E-10);
        }
    }

    @Test
    public void testConstructor() {
        var actual = new TimeSeries(catPopulation, 1992, 1993);

        var expected = new TimeSeries();
        expected.put(1992, 100.0);
        assertEquals(expected, actual);
    }

    @Test
    public void testDivide() {
        dogPopulation.put(1991, 10.0);
        dogPopulation.put(1992, 40.0);
        var actual = catPopulation.dividedBy(dogPopulation);

        var expected = new TimeSeries();
        expected.put(1991, 0.0);
        expected.put(1992, 2.5);
        expected.put(1994, 0.5);
        assertEquals(expected, actual);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDivideException() {
        catPopulation.dividedBy(dogPopulation);
    }
} 