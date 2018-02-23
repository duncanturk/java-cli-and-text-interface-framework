package com.duncanturk.cli.api.filter;

import com.duncanturk.cli.api.TaskTerminationType;

import java.util.Optional;
import java.util.function.Function;

public interface InputFilter extends Function<String, Optional<TaskTerminationType>> {
}
