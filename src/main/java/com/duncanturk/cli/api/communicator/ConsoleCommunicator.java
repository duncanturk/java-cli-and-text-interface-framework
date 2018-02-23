package com.duncanturk.cli.api.communicator;

public class ConsoleCommunicator extends IOStreamCommunicator {
    public ConsoleCommunicator() {
        super(System.in, System.out);
    }
}
