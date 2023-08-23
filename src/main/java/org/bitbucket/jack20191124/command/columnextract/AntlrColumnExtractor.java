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

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.bitbucket.jack20191124.alexander.generated.ColumnExtractLexer;
import org.bitbucket.jack20191124.alexander.generated.ColumnExtractParser;

import net.jcip.annotations.Immutable;
import net.jcip.annotations.ThreadSafe;

/**
 * ANTLR implementation of {@link ColumnExtractor}.
 */
@Immutable
@ThreadSafe
public class AntlrColumnExtractor implements ColumnExtractor {

    @Override
    public void extract(final String file, final int col) {
        final ANTLRInputStream fileStream = new ANTLRInputStream(file);
        final ColumnExtractLexer lexer = new ColumnExtractLexer(fileStream);
        final CommonTokenStream tokens = new CommonTokenStream(lexer);
        final ColumnExtractParser parser = new ColumnExtractParser(tokens, col);
        parser.setBuildParseTree(false); // don't waste time building a tree
        parser.file();
    }
}
