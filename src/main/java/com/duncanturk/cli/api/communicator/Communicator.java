package com.duncanturk.cli.api.communicator;

import com.duncanturk.cli.api.TaskTerminationType;
import com.duncanturk.cli.api.filter.InputFilter;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import static com.duncanturk.cli.api.communicator.CounterStyle.*;

public interface Communicator {
    void print(String text);

    Optional<TaskTerminationType> askString(String question, Consumer<String> handler);

    default TaskTerminationType ask(List<String> options, List<Supplier<TaskTerminationType>> actions) {
        AtomicReference<Supplier<TaskTerminationType>> action = new AtomicReference<>();
        Optional<TaskTerminationType> askRes = ask(options, actions::get, action::set);
        return askRes.orElseGet(action.get());
    }

    default <T> Optional<TaskTerminationType> ask(List<String> options, IntFunction<? extends T> converter, Consumer<? super T> handler, CounterStyle counterStyle) {
        int optionChar = 0;
        Map<String, Integer> actionMap = new HashMap<>();
        StringBuilder sb = new StringBuilder();
        if (counterStyle == AUTO)
            counterStyle = options.size() > 26 ? DECIMAL : LOWER_ALPHA;
        for (String optionString : options) {
            String str = counterStyle.get(optionChar);
            actionMap.put(str, optionChar);
            sb.append("(").append(str).append(") ").append(optionString).append("\n");
            optionChar++;
        }
        sb.deleteCharAt(sb.length() - 1);

        AtomicInteger selected = new AtomicInteger(-1);
        Optional<TaskTerminationType> ret = ask(sb.toString(), actionMap::get, selected::set);
        if (ret.isPresent()) {
            return ret;
        } else if (selected.get() >= 0) {
            handler.accept(converter.apply(selected.get()));
            return Optional.empty();
        } else {
            return ask(options, converter, handler);
        }
    }

    default <T> Optional<TaskTerminationType> ask(List<String> options, IntFunction<? extends T> converter, Consumer<? super T> handler) {
        return ask(options, converter, handler, AUTO);
    }

    default <O, T> Optional<TaskTerminationType> ask(List<? extends O> options, Function<? super O, ? extends T> converter, Consumer<? super T> handler) {
        return ask(options.stream().map(Object::toString).collect(Collectors.toList()), ((Function<Integer, O>) options::get).andThen(converter)::apply, handler);
    }

    default <O> Optional<TaskTerminationType> ask(List<? extends O> options, Consumer<? super O> handler) {
        return ask(options, o -> o, handler);
    }

    default <T> Optional<TaskTerminationType> askString(List<String> options, Function<String, ? extends T> converter, Consumer<? super T> handler) {
        return ask(options, ((Function<Integer, String>) options::get).andThen(converter)::apply, handler);
    }

    default Optional<TaskTerminationType> askInt(String question, Consumer<Integer> handler) {
        return ask(question, Integer::parseInt, handler);
    }

    default Optional<TaskTerminationType> askLong(String question, Consumer<Long> handler) {
        return ask(question, Long::parseLong, handler);
    }

    default Optional<TaskTerminationType> askDouble(String question, Consumer<Double> handler) {
        return ask(question, Double::parseDouble, handler);
    }

    default <T> Optional<TaskTerminationType> ask(String question, Function<String, T> converter, Consumer<T> handler) {
        return askString(question, v -> handler.accept(converter.apply(v)));
    }

    default Optional<TaskTerminationType> filterInput(String s) {
        return getFilters().stream().map(filter -> filter.apply(s)).flatMap(Optional::stream).findAny();
    }

    Collection<InputFilter> getFilters();
}
