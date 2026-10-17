
package org.example.tdd.gpasystem;

import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

public class GradingPolicyManagerTest {

    GradingPolicyManager policy =
            GradingPolicyManager.plusMinusFourPointScale();

    // Test 1: Test each cutoff and just below
    @Test
    void testGradeCutoffs() {

        double[] cutoffs = {
            90, 85, 80, 75, 70, 65,
            60, 55, 50, 45, 40
        };

        double[] lowerCutoffs = {
            85, 80, 75, 70, 65, 60,
            55, 50, 45, 40, 0
        };

        for (int i = 0; i < cutoffs.length; i++) {

            double cutoff = cutoffs[i];
            double justBelow = cutoff - 0.01;

            // Exactly at cutoff uses the higher grade
            assertSame(
                policy.getGradeForPercentage(cutoff),
                policy.getGradeForPercentage(cutoff + 0.01)
            );

            // Just below cutoff uses lower grade
            assertSame(
                policy.getGradeForPercentage(lowerCutoffs[i]),
                policy.getGradeForPercentage(justBelow)
            );

            // Both grades should be different
            assertNotSame(
                policy.getGradeForPercentage(cutoff),
                policy.getGradeForPercentage(justBelow)
            );
        }
    }

    // Test 2: Boundary values
    @Test
    void testMinimumAndMaximum() {
        assertNotNull(policy.getGradeForPercentage(0));
        assertNotNull(policy.getGradeForPercentage(100));
    }

    // Test 3: Invalid percentages
    @Test
    void testInvalidPercentages() {
        double[] invalidValues = {
            -1, 101, Double.NaN,
            Double.POSITIVE_INFINITY,
            Double.NEGATIVE_INFINITY
        };

        for (double value : invalidValues) {
            assertThrows(
                IllegalArgumentException.class,
                () -> policy.getGradeForPercentage(value)
            );
        }
    }

    // Test 4: Empty policy
    @Test
    void testEmptyPolicy() {
        assertThrows(
            IllegalArgumentException.class,
            () -> new GradingPolicyManager(Collections.emptyList())
        );
    }

    // Test 5: Incomplete policy (no 0% band)
    @Test
    void testIncompletePolicy() {
        assertThrows(
            IllegalArgumentException.class,
            () -> new GradingPolicyManager(Arrays.asList(
                new GradeBand(90, new Grade("A", 4.0)),
                new GradeBand(80, new Grade("B", 3.0))
            ))
        );
    }

    // Test 6: Duplicate cutoff
    @Test
    void testDuplicateCutoff() {
        assertThrows(
            IllegalArgumentException.class,
            () -> new GradingPolicyManager(Arrays.asList(
                new GradeBand(90, new Grade("A", 4.0)),
                new GradeBand(90, new Grade("B", 3.0)),
                new GradeBand(0, new Grade("F", 0.0))
            ))
        );
    }

    // Test 7: Null policy
    @Test
    void testNullPolicy() {
        assertThrows(
            NullPointerException.class,
            () -> new GradingPolicyManager(null)
        );
    }

    // Test 8: Null grade band
    @Test
    void testNullGradeBand() {
        assertThrows(
            NullPointerException.class,
            () -> new GradingPolicyManager(Arrays.asList(
                new GradeBand(0, new Grade("F", 0.0)),
                null
            ))
        );
    }
}
