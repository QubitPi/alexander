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
package org.bitbucket.jack20191124.command.columnextract

import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Unroll

abstract class ColumnExtractorSpec extends Specification {

    @Subject
    ColumnExtractor extractor

    /**
     * Returns a ColumnExtractor instance to be tested.
     *
     * @return test subject
     */
    abstract ColumnExtractor getInstance()

    def setup() {
        extractor = getInstance()
    }

    @Unroll
    def "The #index column is '#expected'"() {
        setup: "redirect print to testable location"
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream()
        System.setOut(new PrintStream(outputStream))

        when: "print extracted column"
        extractor.extract(rowsOfData(), index)
        outputStream.flush()
        String allWrittenLines = new String(outputStream.toByteArray())

        then: "the extracted column is printed to the specified location"
        allWrittenLines.contains(expected)

        where:
        index || expected
        1     || firstColumn()
        2     || secondColumn()
        3     || thirdColumn()
    }

    String rowsOfData() {
        "parrt\tTerence Parr\t101\ntombu\tTom Burns\t020\nbke\tKevin Edgar\t008\n"
    }

    String firstColumn() {
        "parrt\ntombu\nbke"
    }

    String secondColumn() {
        "Terence Parr\nTom Burns\nKevin Edgar"
    }

    String thirdColumn() {
        "101\n020\n008"
    }
}
