package com.duncanturk.cli.api.communicator;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static com.duncanturk.cli.api.communicator.CounterStyle.LOWER_ALPHA;
import static org.junit.jupiter.api.Assertions.assertEquals;

class CounterStyleTest {

    @ParameterizedTest
    @CsvSource({"1,b", "0,a", "25,z", "26,aa", "27,ab", "701,zz", "702,aaa"})
    void testLowerAlpha(int index, String str) {
        assertEquals(str, LOWER_ALPHA.get(index));
    }

    @ParameterizedTest
    @CsvSource({"1,2", "0,1", "5,6"})
    void testDecimal(int index, String str) {
        assertEquals(str, CounterStyle.DECIMAL.get(index));
    }
}
