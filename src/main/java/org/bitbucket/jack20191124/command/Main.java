/*
 * Copyright Jiaiq Liu
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.bitbucket.jack20191124.command;

import static org.bitbucket.jack20191124.io.IOUtils.printErrorStream;

import org.bitbucket.jack20191124.command.arithmetic.EvaluateCommand;
import org.bitbucket.jack20191124.command.columnextract.ColumnExtractCommand;
import org.bitbucket.jack20191124.command.interfaceextract.InterfaceExtractCommand;
import org.bitbucket.jack20191124.annotation.NotNull;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Stream;

/**
 * Command-line driver.
 */
public class Main {

    private static final Map<String, Command> COMMANDS = new HashMap<>();
    private static final int MAX_COMMAND_NAME_LEN;

    static {
        loadAllCommands();
        MAX_COMMAND_NAME_LEN = computeMaxCommandNameLen();
    }

    /**
     * Makes all implemented {@link Command}s executable at run-time for user.
     */
    private static void loadAllCommands() {
        getAllCommands().forEach(Main::loadCommand);
    }

    /**
     * Returns a stream of {@link Command}s that shall be executable at run-time
     * <p>
     * This method should return all implemented commands.
     *
     * @return all defined {@link Command}s
     */
    private static Stream<Command> getAllCommands() {
        return Stream.of(
                new InterfaceExtractCommand(),
                new ColumnExtractCommand(),
                new EvaluateCommand()
        );
    }

    /**
     * Makes a specified {@link Command} executable at run-time for user.
     *
     * @param command  The command to be executed by user
     *
     * @throws NullPointerException if the provided {@link Command} object is {@code null}
     */
    private static void loadCommand(@NotNull final Command command) {
        Objects.requireNonNull(command, "command");

        final Command prev = COMMANDS.put(command.getName(), command);

        if (prev != null) {
            throw new AssertionError(String.format("Two commands have the same name: %s, %s", command, prev));
        }
    }

    /**
     * Returns the length(num. of chars) of the longest {@link Command#getName() command name} among all executable
     * {@link Command}s.
     *
     * @return longest length among {@link Command#getName() command names}
     */
    @SuppressWarnings("OptionalGetWithoutIsPresent")
    private static int computeMaxCommandNameLen() {
        return COMMANDS.keySet().stream()
                .map(String::length)
                .max(Integer::compareTo)
                .get();
    }


    /**
     * The entry point of command execution.
     *
     * @param args  A list of command argument. The first argument is the name of command followed by a list of command
     * arguments
     */
    @SuppressWarnings("checkstyle:UncommentedMain")
    public static void main(@NotNull final String[] args) {
        System.exit(run(args));
    }

    /**
     * Runs a specific command specified by command-line arguments.
     *
     * @param args  A list of command argument. The first argument is the name of command followed by a list of command
     * arguments
     *
     * @return result code (0 for success)
     */
    @NotNull
    private static int run(@NotNull final String[] args) {
        if (args.length != 0) {
            final Command command = COMMANDS.get(args[0]);
            if (command != null) {
                return command.run(System.in, System.out, System.err, Arrays.asList(args).subList(1, args.length));
            }
        }

        printHeaderInfo();
        printAvailableCommands();

        return 1;
    }

    /**
     * Prints copyright info as header to standard err output.
     * <p>
     * The format is
     * <pre>
     * Alexander
     *
     * Copyright header...
     * ...
     * ...
     * </pre>
     */
    private static void printHeaderInfo() {
        System.err.println("Alexander\n");

        try (InputStream headerStream = loadHeader()) {
            printErrorStream(headerStream);
        } catch (final IOException exception) {
            throw new IllegalStateException("Error while reading copyright.txt", exception);
        }
    }

    /**
     * Loads the file content of copyright info as command out header and returns it as stream.
     *
     * @return string stream
     */
    private static InputStream loadHeader() {
        return Main.class.getClassLoader().getResourceAsStream("copyright.txt");
    }

    /**
     * Prints names and descriptions of all loaded {@link Command}s to standard err output.
     * <p>
     * All names are all right-aligned and all descriptions are right-aligned. For example:
     * <pre>
     * ------------------
     * Available commands
     *    extractColumn  A command that prints out a specific column from rows of data.
     * simpleArithmetic  A command that performs simple arithmetic calculations ...
     * extractInterface  A command that generates a Java interface file from the methods in a Java class definition.
     * </pre>
     */
    private static void printAvailableCommands() {
        System.err.println("------------------");
        System.err.println("Available commands");
        for (final Command command : COMMANDS.values()) {
            System.err.printf("%" + MAX_COMMAND_NAME_LEN + "s  %s\n", command.getName(), command.getShortDescription());
        }
    }
}
