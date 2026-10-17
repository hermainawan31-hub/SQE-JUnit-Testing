
package org.example.tdd.gpasystem;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class GradeBandTest {

    @Test
    void testValidGradeBand() {
        Grade grade = new Grade("A-", 3.7);
        GradeBand band = new GradeBand(85.0, grade);

        assertEquals(85.0, band.getMinimumPercentage());
        assertSame(grade, band.getGrade());
    }

    @Test
    void testNegativePercentage() {
        assertThrows(
            IllegalArgumentException.class,
            () -> new GradeBand(-1, new Grade("A", 4.0))
        );
    }

    @Test
    void testPercentageAbove100() {
        assertThrows(
            IllegalArgumentException.class,
            () -> new GradeBand(101, new Grade("A", 4.0))
        );
    }

    @Test
    void testNaNPercentage() {
        assertThrows(
            IllegalArgumentException.class,
            () -> new GradeBand(Double.NaN, new Grade("A", 4.0))
        );
    }

    @Test
    void testNullGrade() {
        assertThrows(
            NullPointerException.class,
            () -> new GradeBand(85, null)
        );
    }
}
