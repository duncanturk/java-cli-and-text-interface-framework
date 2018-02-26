package com.duncanturk.cli.api.communicator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.*;

class CommunicatorTest {
    private Communicator communicator;

    @BeforeEach
    void beforeEach() {
    }

    @Test
    void testAskStringValue() {
        communicator = new PreparedInputCommunicator("a", "java", "", "\r");
        AtomicReference<String> val = new AtomicReference<>(null);
        assertFalse(communicator.askString("say a", val::set).isPresent());
        assertEquals("a", val.get());
        assertFalse(communicator.askString("say java", val::set).isPresent());
        assertEquals("java", val.get());
        assertFalse(communicator.askString("silence!", val::set).isPresent());
        assertEquals("", val.get());
        assertFalse(communicator.askString("make a step", val::set).isPresent());
        assertEquals("\r", val.get());
    }

    @Test
    void testAskDoubleValue() {
        communicator = new PreparedInputCommunicator("0", "0.0", "42", "-3.5", "5e3");
        AtomicReference<Double> val = new AtomicReference<>();
        assertFalse(communicator.askDouble("one minus one ", val::set).isPresent());
        assertEquals(Double.valueOf(0), val.get());
        assertFalse(communicator.askDouble("tow times zero is ", val::set).isPresent());
        assertEquals(Double.valueOf(0), val.get());
        assertFalse(communicator.askDouble("the answer is ", val::set).isPresent());
        assertEquals(Double.valueOf(42), val.get());
        assertFalse(communicator.askDouble("-3.5", val::set).isPresent());
        assertEquals(Double.valueOf(-3.5), val.get());
        assertFalse(communicator.askDouble("0100000010110011100010000000000000000000000000000000000000000000", val::set).isPresent());
        assertEquals(Double.valueOf(5000), val.get());
    }
}
