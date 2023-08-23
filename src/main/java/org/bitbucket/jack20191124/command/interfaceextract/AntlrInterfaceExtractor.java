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

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Parser;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.bitbucket.jack20191124.alexander.generated.InterfaceExtractBaseListener;
import org.bitbucket.jack20191124.alexander.generated.InterfaceExtractLexer;
import org.bitbucket.jack20191124.alexander.generated.InterfaceExtractParser;

import net.jcip.annotations.Immutable;
import net.jcip.annotations.ThreadSafe;

/**
 * ANTLR implementaitn of {@link InterfaceExtractor}.
 */
@Immutable
@ThreadSafe
public class AntlrInterfaceExtractor implements InterfaceExtractor {

    /**
     * Prints out the interface header when we see the start of a class definition. Then we will print a terminating
     * {@code }} at the end of the interface definition. Upon each method definition, we will split out its
     * signature.
     */
    private static class ExtractInterfaceListener extends InterfaceExtractBaseListener {

        private static final String VOID = "void";

        private final InterfaceExtractParser parser;

        /**
         * Constructs a {@link ExtractInterfaceListener} with the specified ANTLR {@link Parser}.
         *
         * @param parser  The provided ANTLR {@link Parser}
         */
        private ExtractInterfaceListener(final InterfaceExtractParser parser) {
            this.parser = parser;
        }

        @Override
        public void enterClassDeclaration(final InterfaceExtractParser.ClassDeclarationContext ctx) {
            System.out.println(String.format("public interface %s {", ctx.Identifier()));
        }

        @Override
        public void exitClassDeclaration(final InterfaceExtractParser.ClassDeclarationContext ctx) {
            System.out.println("}");
        }

        @Override
        public void enterMethodDeclaration(final InterfaceExtractParser.MethodDeclarationContext ctx) {
            final TokenStream tokens = getParser().getTokenStream();

            String type = VOID;
            if (ctx.type() != null) {
                type = tokens.getText(ctx.type());
            }

            final String args = tokens.getText(ctx.formalParameters());

            System.out.println(
                    String.format("\t%s %s%s;", type, ctx.Identifier(), args)
                            .replace("\t", "    ") // replace tab with 4 spaces for method declar
            );
        }

        private InterfaceExtractParser getParser() {
            return parser;
        }
    }

    @Override
    public void parse(final String file) {
        final ANTLRInputStream fileStream = new ANTLRInputStream(file);
        final InterfaceExtractLexer lexer = new InterfaceExtractLexer(fileStream);
        final CommonTokenStream tokens = new CommonTokenStream(lexer);
        final InterfaceExtractParser parser = new InterfaceExtractParser(tokens);
        final ParseTree tree = parser.compilationUnit(); // parse
        final ParseTreeWalker walker = new ParseTreeWalker(); // create standard walker
        final ExtractInterfaceListener extractor = new ExtractInterfaceListener(parser);
        walker.walk(extractor, tree); // initiate walk of tree with listener
    }
}
