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

/**
 * Implements command that evaluates an arithmetic expression.
 * <p>
 * The arithmetic expression and evaluation rules are defined in
 * <pre>
 *                            +-------------------------+
 *                            |                         |
 *                            |          Main           |
 *                            |                         |
 *                            +-------------------------+
 *                                   |            ^
 *                    user CML input |            | CML output
 *                                   |            |
 * ----------------------------------|------------|-------------------------------------------------------
 *                                   |            |
 *                                   v            |
 *                            +-------------------------+
 *                            |                         |       provides ArithmeticExprEvaluator instances
 *                            | EvaluateCommand         |<----------------------+
 *                            |                         |                       |
 *                            +-------------------------+                       |
 *                                   |            ^                             |
 *                                   |            |                             |
 *  ---------------------------------|------------|-----------------------------|--------------------------
 *                                   |            |                             |
 *             arithmetic expression |            | numeric result              |
 *                                   v            |                             |
 *                            +-------------------------+                       |
 *                            |                         |          +-------------------------+
 *                            | ArithmeticExprEvaluator |          |                         |
 *                            |                         |          | SimpleArithmeticFactory |
 *                            |       (interface)       |          |                         |
 *                            |                         |          +-------------------------+
 *                            +-------------------------+                       ^
 *                                         ^                                    |
 *                                         |                                    |
 *                                         | implements                         |
 *                                         |                                    |
 *                           +--------------------------+                       |
 *                           |                          |                       |
 *                           | AntlrIntEvaluator        |-----------------------+
 *                           |                          |
 *                           +--------------------------+
 * </pre>
 */
package org.bitbucket.jack20191124.command.arithmetic;
