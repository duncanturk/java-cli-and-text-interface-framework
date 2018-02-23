package com.duncanturk.cli.api.communicator;

import java.util.Stack;

public class PreparedInputCommunicator extends AbstractCommunicator {
    private Stack<String> inputs = new Stack<>();

    PreparedInputCommunicator(String... inputs) {
        for (int i = inputs.length - 1; i >= 0; --i) {
            this.inputs.add(inputs[i]);
        }
    }

    @Override
    public void print(String text) {
    }

    @Override
    protected String read() {
        return inputs.pop();
    }
}
