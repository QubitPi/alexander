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
package org.bitbucket.jack20191124.command.arithmetic;

import org.bitbucket.jack20191124.command.AbstractCommand;

import net.jcip.annotations.Immutable;
import net.jcip.annotations.ThreadSafe;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.List;

/**
 * {@link EvaluateCommand} parses user's input that contains a string arithmetic expression, evaluates it, and
 * prints the evaluation result to standard output.
 * <p>
 * If user's input is invalid, a helper message is printed to the standard error output instead.
 */
@Immutable
@ThreadSafe
public class EvaluateCommand extends AbstractCommand {

    /**
     * Constructor.
     */
    public EvaluateCommand() {
        super(
                "simpleArithmetic",
                "A command that performs simple arithmetic calculations involving +-*/ and parentheses, (), only"
        );
    }

    @Override
    public int run(final InputStream in, final PrintStream out, final PrintStream err, final List<String> args) {
        if (args.size() != 1) {
            printHelp(out);
            return 0;
        }

        System.out.println(SimpleArithmeticFactory.newInstance().compute(args.get(0)));

        return 0;
    }

    @Override
    protected void printHelp(final PrintStream out) {
        out.println("performs simple arithmetic calculations");
        out.println();
        out.println(getShortDescription());
        out.println("expr   the arithmetic expression");
    }
}
