package flik;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class FlikTest {
    @Test
    public void generalTest() {
        int i = 0;
        for (int j = 0; i < 500; ++i, ++j) {
            assertTrue(Flik.isSameNumber(i, j));
        }
    }
}
