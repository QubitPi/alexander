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
package org.bitbucket.jack20191124.command.interfaceextract

import spock.lang.Specification
import spock.lang.Subject

abstract class InterfaceExtractorSpec extends Specification {

    @Subject
    InterfaceExtractor extractor

    abstract InterfaceExtractor getInstance()

    def setup() {
        extractor = getInstance()
    }

    def "Class file is parsed into an interface"() {
        setup: "redirect print to testable location"
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream()
        System.setOut(new PrintStream(outputStream))

        when: "class file is parsed"
        extractor.parse(new File("src/test/resources/InterfaceExtractorTestFile.java").text)
        outputStream.flush()
        String allWrittenLines = new String(outputStream.toByteArray())

        then: "the interface is printed in the specified location"
        allWrittenLines == new File("src/test/resources/ExpectedInterface.java").text
    }
}
