package com.duncanturk.cli.api.communicator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Optional;

import static com.duncanturk.cli.api.TaskTerminationType.*;

public class IOStreamCommunicator extends AbstractCommunicator {
    private final BufferedReader br;
    private final PrintStream ps;

    public IOStreamCommunicator(InputStream inputStream, OutputStream outputStream) {
        br = new BufferedReader(new InputStreamReader(inputStream));
        ps = new PrintStream(outputStream);
        getFilters().add(str ->
                str.toLowerCase().equals("exit") ? Optional.of(EXIT) : Optional.empty());
        getFilters().add(str ->
                str.toLowerCase().equals("cancel") ? Optional.of(CANCELED) : Optional.empty());
    }

    @Override
    public void print(String text) {
        ps.println(text);
    }

    @Override
    protected String read() {
        try {
            return br.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }
}
