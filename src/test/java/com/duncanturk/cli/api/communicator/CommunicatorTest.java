package com.duncanturk.cli.api.communicator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CommunicatorTest {
    private Communicator communicator;

    @BeforeEach
    void beforeEach() {
    }

    @Test
    void testAskStringValue() {
        communicator = new PreparedInputCommunicator("a", "java", "", "\r");
        AtomicReference<String> val = new AtomicReference<>(null);
        communicator.askString("say a", val::set);
        assertEquals("a", val.get());
        communicator.askString("say java", val::set);
        assertEquals("java", val.get());
        communicator.askString("silence!", val::set);
        assertEquals("", val.get());
        communicator.askString("make a step", val::set);
        assertEquals("\r", val.get());
    }

    @Test
    void testAskDoubleValue() {
        communicator = new PreparedInputCommunicator("0", "0.0", "42", "-3.5", "5e3");
        AtomicReference<Double> val = new AtomicReference<>();
        communicator.askDouble("one minus one ", val::set);
        assertEquals(Double.valueOf(0), val.get());
        communicator.askDouble("tow times zero is ", val::set);
        assertEquals(Double.valueOf(0), val.get());
        communicator.askDouble("the answer is ", val::set);
        assertEquals(Double.valueOf(42), val.get());
        communicator.askDouble("-3.5", val::set);
        assertEquals(Double.valueOf(-3.5), val.get());
        communicator.askDouble("0100000010110011100010000000000000000000000000000000000000000000", val::set);
        assertEquals(Double.valueOf(5000), val.get());
    }
}
