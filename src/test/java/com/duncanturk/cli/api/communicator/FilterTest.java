package com.duncanturk.cli.api.communicator;

import com.duncanturk.cli.api.TaskTerminationType;
import com.duncanturk.cli.api.filter.InputFilter;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static com.duncanturk.cli.api.TaskTerminationType.*;
import static org.junit.jupiter.api.Assertions.*;

class FilterTest {

    @Test
    void testFilter() {
        Communicator communicator = new PreparedInputCommunicator("cancel", "cancel", "cancel", "cancel", "CANCEL", "c", "exit", "e", "EXIT");
        InputFilter cancelFilter = str -> str.equals("cancel") || str.equals("c") ? Optional.of(CANCELED) : Optional.empty();
        InputFilter exitFilter = str -> str.equals("exit") ? Optional.of(EXIT) : Optional.empty();
        assertFalse(communicator.askString("question", s -> {
        }).isPresent());

        communicator.getFilters().add(cancelFilter);
        communicator.getFilters().add(exitFilter);

        Optional<TaskTerminationType> ret = communicator.askString("", s -> {
        });
        assertTrue(ret.isPresent());
        assertEquals(CANCELED, ret.get());

        communicator.getFilters().remove(cancelFilter);

        assertFalse(communicator.askString("", s -> {
        }).isPresent());

        communicator.getFilters().add(cancelFilter);

        ret = communicator.askString("", s -> {
        });
        assertTrue(ret.isPresent());
        assertEquals(CANCELED, ret.get());

        assertFalse(communicator.askString("", s -> {
        }).isPresent());

        ret = communicator.askString("", s -> {
        });
        assertTrue(ret.isPresent());
        assertEquals(CANCELED, ret.get());

        ret = communicator.askString("", s -> {
        });
        assertTrue(ret.isPresent());
        assertEquals(EXIT, ret.get());

        assertFalse(communicator.askString("", s -> {
        }).isPresent());
        assertFalse(communicator.askString("", s -> {
        }).isPresent());
    }
}
