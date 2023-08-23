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
package org.bitbucket.jack20191124.command.arithmetic

import spock.lang.Specification
import spock.lang.Subject

abstract class ArithmeticExprEvaluatorSpec extends Specification {

    @Subject
    ArithmeticExprEvaluator simpleArithmetic

    abstract ArithmeticExprEvaluator newInstance()

    def setup() {
        simpleArithmetic = newInstance()
    }

    def "Compute accumulates results"() {
        expect:
        simpleArithmetic.compute("193") == 193
        simpleArithmetic.compute("a = 5") == 5
        simpleArithmetic.compute("b = 6") == 6
        simpleArithmetic.compute("a + b * 2") == 17
        simpleArithmetic.compute("(1+2)*3") == 9
    }
}
