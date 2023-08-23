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

/**
 * A simple arithmetic calculator that evaluates a arithmetic expression.
 * <p>
 * {@link ArithmeticExprEvaluator} allows only the basic arithmetic operators (add, subtract, multiply, and divide),
 * parenthesized expressions, doubles, and variables. Operations on {@link ArithmeticExprEvaluator} instance should be
 * accumulative, for example:
 * <pre>
 * {@code
 * ArithmeticExprEvaluator simpleArithmetic = new ArithmeticExprEvaluator();
 *
 * simpleArithmetic.compute("193") -> 193
 * simpleArithmetic.compute("a = 5") -> 5
 * simpleArithmetic.compute("b = 6") -> 6
 * simpleArithmetic.compute("a + b * 2") -> 17
 * simpleArithmetic.compute("(1+2)*3") -> 9
 * }
 * </pre>
 * This means instance of {@link ArithmeticExprEvaluator} is assumed to be stateful.
 * <p>
 * This is a {@link java.util.function functional interface} whose functional method is {@link #compute(String)}.
 */
@FunctionalInterface
public interface ArithmeticExprEvaluator {

    /**
     * Takes an arithmetic expression as string and computes its arithmetic results.
     * <p>
     * For example, when expression is "(1 + 2) / 2", return 1.5
     *
     * @param expr  The arithmetic expression
     *
     * @return evaluated expression result
     */
    double compute(String expr);
}
