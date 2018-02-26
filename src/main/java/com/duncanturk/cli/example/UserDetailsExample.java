package com.duncanturk.cli.example;

import com.duncanturk.cli.api.TaskTerminationType;
import com.duncanturk.cli.api.communicator.Communicator;
import com.duncanturk.cli.api.communicator.ConsoleCommunicator;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.duncanturk.cli.api.TaskTerminationType.*;

public class UserDetailsExample {
    public static void main(String... args) {
        new UserDetailsExample().start();
    }

    private Communicator communicator = new ConsoleCommunicator();
    private List<UserDetails> users = new ArrayList<>();

    private void start() {
        while (communicator.ask(List.of(
                "Create a new User",
                "Manage existing users"),
                List.of(
                        this::createUser,
                        this::manageUsers
                )) != EXIT) ;

    }

    private TaskTerminationType createUser() {
        UserDetails details = new UserDetails();
        Optional<TaskTerminationType> earlyExit =
                communicator.askString("What's your name?", details::setName).or(
                        () -> communicator.askString("Your email?", details::setEmail)).or(
                        () -> communicator.askInt("How old are you?", details::setAge));
        if (earlyExit.isPresent())
            return earlyExit.get();

        users.add(details);

        return SUCCESS;
    }

    private TaskTerminationType manageUsers() {
        return communicator.ask(List.of("remove user", "change email"),
                List.of(() -> communicator.ask(users, users::remove).orElse(SUCCESS),
                        () -> communicator.ask(users, this::changeEmail).orElse(SUCCESS))
        );
    }

    private Optional<TaskTerminationType> changeEmail(UserDetails userDetails) {
        return communicator.askString("New Email:", userDetails::setEmail);
    }

    static class UserDetails {
        private String name, email;
        private int age;

        public void setName(String name) {
            this.name = name;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public void setAge(int age) {
            this.age = age;
        }

        @Override
        public String toString() {
            return name + "<" + email + ">";
        }
    }
}
