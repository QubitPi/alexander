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
package org.bitbucket.jack20191124.command.columnextract;

/**
 * Prints out a specific column from rows of data.
 * <p>
 * The columns are tab-delimited and each row ends with a new line character. For example
 * <pre>
 * parrt    Terence Parr    101
 * tombu    Tom Burns       020
 * bke      Kevin Edgar     008
 * </pre>
 *
 * This is a {@link java.util.function functional interface} whose functional method is {@link #extract(String, int)}.
 */
@FunctionalInterface
public interface ColumnExtractor {

    /**
     * Prints out a specific column from rows of data in a specified file.
     * <p>
     * For example
     *
     * <pre>
     * parrt    Terence Parr    101
     * tombu    Tom Burns       020
     * bke      Kevin Edgar     008
     * </pre>
     *
     * {@code extract("path/to/example/file/above", 1)} should prints the following to terminal
     *
     * <pre>
     * parrt
     * tombu
     * bke
     * </pre>
     *
     * @param file  The specified file content in a single String
     * @param col  The column (counting from 1) to be printed
     */
    void extract(String file, int col);
}
