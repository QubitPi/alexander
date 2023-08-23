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
package org.bitbucket.jack20191124.command.interfaceextract;

/**
 * An object that generates a Java interface file from the methods in a Java class definition.
 * <p>
 * This is a {@link java.util.function functional interface} whose functional method is {@link #parse(String)}.
 */
@FunctionalInterface
public interface InterfaceExtractor {

    /**
     * Prints a Java interface source code from the methods in a Java class definition file.
     * <p>
     * For example
     * <pre>
     * {@code
     * // Copyright Jiaiq Liu
     * //
     * // Licensed under the Apache License, Version 2.0 (the "License");
     * // you may not use this file except in compliance with the License.
     * // You may obtain a copy of the License at
     * //
     * //     http://www.apache.org/licenses/LICENSE-2.0
     * //
     * // Unless required by applicable law or agreed to in writing, software
     * // distributed under the License is distributed on an "AS IS" BASIS,
     * // WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
     * // See the License for the specific language governing permissions and
     * // limitations under the License.
     * package org.bitbucket.jack20191124.alexander.command.interfaceextract;
     *
     * import AbstractCommand;
     * import java.io.InputStream;
     * import java.io.PrintStream;
     * import java.nio.file.Files;
     * import java.nio.file.Paths;
     * import java.util.List;
     *
     * public class ExtractInterfaceCommand extends AbstractCommand {
     *
     *     public ExtractInterfaceCommand() {
     *         super(
     *               "extractInterface",
     *               "a command that generates a Java interface file from the methods in a Java class definition"
     *               );
     *     }
     *
     *     @Override
     *     public int run(final InputStream in, final PrintStream out, final PrintStream err, final List<String> args)
     *         throws Exception {
     *         if (args.size() != 1) {
     *             printHelp(out);
     *             return 0;
     *         }
     *         new AntlrInterfaceExtractor().parse(new String(Files.readAllBytes(Paths.get(args.get(0)))));
     *         return 0;
     *     }
     *     @Override
     *     protected void printHelp(final PrintStream out) {
     *         out.println("extractInterface file");
     *         out.println();
     *         out.println(getShortDescription());
     *         out.println("file   the absolute file path to be extracted");
     *     }
     * }
     * }
     * </pre>
     * The following interface will be printed in standard output
     * <pre>
     * {@code
     * public interface ExtractInterfaceCommand {
     *     int run(final InputStream in, final PrintStream out, final PrintStream err, final List<String> args);
     *     void printHelp(final PrintStream out);
     * }
     * }
     * </pre>
     *
     * @param file  The specified file content in a single String
     */
    void parse(String file);
}
