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

import static org.bitbucket.jack20191124.io.IOUtils.readFileAsString;

import org.bitbucket.jack20191124.command.AbstractCommand;

import net.jcip.annotations.Immutable;
import net.jcip.annotations.ThreadSafe;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.List;

/**
 * A command that generates a Java interface file from the methods in a Java class definition.
 * <p>
 * See {@link InterfaceExtractor#parse(String)} for an example.
 */
@Immutable
@ThreadSafe
public class InterfaceExtractCommand extends AbstractCommand {

    /**
     * Constructor.
     */
    public InterfaceExtractCommand() {
        super(
                "extractInterface",
                "A command that generates a Java interface file from the methods in a Java class definition."
        );
    }

    @Override
    public int run(final InputStream in, final PrintStream out, final PrintStream err, final List<String> args) {
        if (args.size() != 1) {
            printHelp(out);
            return 0;
        }

        new AntlrInterfaceExtractor().parse(readFileAsString(args.get(0)));

        return 0;
    }

    @Override
    protected void printHelp(final PrintStream out) {
        out.println("extractInterface file");
        out.println();
        out.println(getShortDescription());
        out.println("file   the absolute file path to be extracted");
    }
}
