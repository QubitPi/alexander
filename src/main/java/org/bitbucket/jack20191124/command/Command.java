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

import java.io.InputStream;
import java.io.PrintStream;
import java.util.List;

/**
 * An object that performs one utility function.
 */
public interface Command {

    /**
     * Runs the command with supplied arguments.
     *
     * @param in  Input stream to read data (typically System.in).
     * @param out  Output of command (typically System.out).
     * @param err  Error stream (typically System.err).
     * @param args  Non-null list of arguments.
     *
     * @return result code (0 for success)
     */
    int run(InputStream in, PrintStream out, PrintStream err, List<String> args);

    /**
     * Returns the name of this command.
     * <p>
     * This name will be used in command-line listings. Implementor must make this method return a unique name for each
     * different command.
     *
     * @return the name of this command
     */
    String getName();

    /**
     * Returns a 1-line description of the command used in command-line listings.
     *
     * @return the 1-line description of the command
     */
    String getShortDescription();
}
