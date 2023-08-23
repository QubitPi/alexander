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
grammar SimpleArithmetic;

start:  stat+ ;

stat:   expr NEWLINE           # printExpr
    |   ID '=' expr NEWLINE    # assign
    |   NEWLINE                # blank
    ;

expr:   expr op=('*'|'/') expr # MulDiv
    |   expr op=('+'|'-') expr # AddSub
    |   INT                    # int
    |   ID                     # id
    |   '(' expr ')'           # parens
    ;

ID  :   [a-zA-Z]+ ;
INT :   [0-9]+ ;
NEWLINE:'\r'? '\n' ;
WS  :   [ \t]+ -> skip ;

MUL :   '*' ; // assign token name to '*' used above
DIV :   '/' ;
ADD :   '+' ;
SUB :   '-' ;
