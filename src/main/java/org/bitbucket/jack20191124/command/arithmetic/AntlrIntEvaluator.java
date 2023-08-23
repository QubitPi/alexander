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

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.bitbucket.jack20191124.alexander.generated.SimpleArithmeticBaseVisitor;
import org.bitbucket.jack20191124.alexander.generated.SimpleArithmeticLexer;
import org.bitbucket.jack20191124.alexander.generated.SimpleArithmeticParser;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.NotThreadSafe;
import net.jcip.annotations.ThreadSafe;

import java.util.HashMap;
import java.util.Map;

/**
 * A simple arithmetic calculator that evaluates a string arithmetic expression into a numerical integer.
 * <p>
 * {@link ArithmeticExprEvaluator} allows only the basic arithmetic operators (add, subtract, multiply, and divide),
 * parenthesized expressions, integers, and variables. Operations on {@link ArithmeticExprEvaluator} instance is
 * accumulative, LookupConfigToolSpec.javafor example:
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
 */
@ThreadSafe
public class AntlrIntEvaluator implements ArithmeticExprEvaluator {

    /**
     * A visitor that gets arithmetic expression parser to compute values.
     */
    @NotThreadSafe
    private static class SimpleArithmeticVisitor extends SimpleArithmeticBaseVisitor<Integer> {

        /**
         * "Memory" for our calculator; mapping from variable to value.
         */
        private final Map<String, Integer> variableValues;

        /**
         * Constructor.
         */
        private SimpleArithmeticVisitor() {
            variableValues = new HashMap<>();
        }

        @Override
        public Integer visitAssign(final SimpleArithmeticParser.AssignContext ctx) {
            final String id = ctx.ID().getText(); // id is left-hand side of '='
            final int value = visit(ctx.expr());

            getVariableValues().put(id, value);

            return value;
        }

        @Override
        public Integer visitPrintExpr(final SimpleArithmeticParser.PrintExprContext ctx) {
            return visit(ctx.expr());
        }

        @Override
        public Integer visitInt(final SimpleArithmeticParser.IntContext ctx) {
            return Integer.valueOf(ctx.INT().getText());
        }

        @Override
        public Integer visitId(final SimpleArithmeticParser.IdContext ctx) {
            final String id = ctx.ID().getText();
            return getVariableValues().get(id);
        }

        @Override
        public Integer visitMulDiv(final SimpleArithmeticParser.MulDivContext ctx) {
            final int left = visit(ctx.expr(0));
            final int right = visit(ctx.expr(1));

            return ctx.op.getType() == SimpleArithmeticParser.MUL
                    ? left * right
                    : left / right;
        }

        @Override
        public Integer visitAddSub(final SimpleArithmeticParser.AddSubContext ctx) {
            final int left = visit(ctx.expr(0));
            final int right = visit(ctx.expr(1));

            return ctx.op.getType() == SimpleArithmeticParser.ADD
                    ? left + right
                    : left - right;
        }

        @Override
        public Integer visitParens(final SimpleArithmeticParser.ParensContext ctx) {
            return visit(ctx.expr());
        }

        /**
         * Returns the mapping from variable to value.
         *
         * @return a memory block
         */
        private Map<String, Integer> getVariableValues() {
            return variableValues;
        }
    }

    /**
     * The inner calculator that computes arithmetic expressions.
     */
    @GuardedBy("this")
    private final SimpleArithmeticVisitor visitor;

    /**
     * Constructor.
     */
    private AntlrIntEvaluator() {
        visitor = new SimpleArithmeticVisitor();
    }

    /**
     * Creates a new {@link ArithmeticExprEvaluator} instance.
     *
     * @return a new instance
     */
    public static ArithmeticExprEvaluator newInstance() {
        return new AntlrIntEvaluator();
    }

    @Override
    public double compute(final String stat) {
        final ANTLRInputStream inputStream = new ANTLRInputStream(stat);
        final SimpleArithmeticLexer lexer = new SimpleArithmeticLexer(inputStream);
        final CommonTokenStream tokens = new CommonTokenStream(lexer);
        final SimpleArithmeticParser parser = new SimpleArithmeticParser(tokens);

        synchronized (this) {
            return getVisitor().visit(parser.start());
        }
    }

    /**
     * Returns the calculator.
     *
     * @return inner calculator
     */
    private SimpleArithmeticVisitor getVisitor() {
        return visitor;
    }
}
