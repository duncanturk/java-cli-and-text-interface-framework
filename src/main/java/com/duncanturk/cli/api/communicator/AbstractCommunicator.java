package com.duncanturk.cli.api.communicator;

import com.duncanturk.cli.api.TaskTerminationType;
import com.duncanturk.cli.api.filter.InputFilter;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;

public abstract class AbstractCommunicator implements Communicator {
    private Set<InputFilter> inputFilters = new HashSet<>();

    abstract protected String read();

    @Override
    public Collection<InputFilter> getFilters() {
        return inputFilters;
    }

    @Override
    public Optional<TaskTerminationType> askString(String question, Consumer<String> handler) {
        do {
            try {
                print(question);
                String input = read();
                Optional<TaskTerminationType> res = filterInput(input);
                if (!res.isPresent()) {
                    handler.accept(input);
                }
                return res;
            } catch (Exception e) {
                print("an error occurred handling your input!");
            }
        } while (true);
    }
}
