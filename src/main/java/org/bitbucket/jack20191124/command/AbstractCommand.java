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

import net.jcip.annotations.Immutable;
import net.jcip.annotations.ThreadSafe;

import java.io.PrintStream;
import java.util.Objects;

/**
 * A skeleton implementation of {@link Command} interface that offers common states and methods.
 */
@Immutable
@ThreadSafe
public abstract class AbstractCommand implements Command {

    private final String name;
    private final String shortDescription;

    /**
     * Constructor.
     *
     * @param name  The name of this command
     * @param shortDescription  The one-line description of this command
     */
    protected AbstractCommand(final String name, final String shortDescription) {
        this.name = name;
        this.shortDescription = shortDescription;
    }

    /**
     * Prints the help message of this {@link Command} to terminal.
     *
     * @param out  Output stream of the message
     */
    protected abstract void printHelp(PrintStream out);

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getShortDescription() {
        return shortDescription;
    }

    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || getClass() != other.getClass()) {
            return false;
        }

        final AbstractCommand that = (AbstractCommand) other;
        return Objects.equals(getName(), that.getName()) &&
                Objects.equals(getShortDescription(), that.getShortDescription());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getShortDescription());
    }
}
