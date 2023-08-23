[ ![Download](https://api.bintray.com/packages/jack20191124/Maven/Alexander/images/download.svg) ](https://bintray.com/jack20191124/Maven/Alexander/_latestVersion) [ ![Build Status](https://img.shields.io/badge/build-passing-brightgreen.svg) ](https://jack20191124.bitbucket.io/sites/alexander/surefire-report.html) [ ![Code Coverage](https://img.shields.io/badge/coverage-100%25-brightgreen.svg) ](https://jack20191124.bitbucket.io/sites/alexander/jacoco/index.html) [ ![JavaDoc](https://img.shields.io/badge/JavaDoc-1.0-blueviolet.svg) ](https://jack20191124.bitbucket.io/sites/alexander/apidocs/index.html) [ ![License Badge](https://img.shields.io/badge/License-Apache%202.0-orange.svg) ](https://www.apache.org/licenses/LICENSE-2.0)

Alexander
=========

[Alexander](https://bitbucket.org/jack20191124/alexander) is a collection of useful CML utilities.

Alexander the Great was the student of Aristotle.

## Install Alexander

Alexander is written in Java. You need to have Java 8+
[installed](http://www.java.com/en/download/help/download_options.xml) before you begin.

Installing Alexander itself is a matter of
[downloading the latest jar](https://bintray.com/jack20191124/Maven/Alexander) and storing it somewhere appropriate. The
 jar contains all dependencies necessary to run the Alexander commands.

After you [download Alexander from the website](https://bintray.com/jack20191124/Maven/Alexander) using a web
browser, 

```
cd /usr/local/lib
```

On Unix, `/usr/local/lib` is a good directory to store jars like Alexander's. On Windows, there doesn't seem to be a
standard directory, so you can simply store it in your project directory. Most development environments want to you drop
the jar into the dependency list of your language application project. There is no configuration script or configuration
file to alter - you just need to make sure that Java knows how to find the jar.

Because Alexander is a command line tool, you need to go through the typical onerous process of setting
[`CLASSPATH`](http://docs.oracle.com/javase/tutorial/essential/environment/paths.html) environment variable. With
`CLASSPATH` set, Java can find both the Alexander and the runtime library. On Unix systems, you can execute the
following from the shell or add it to the shell start-up script (`.bash_profile` for `bash` shell):

```
export CLASSPATH=".:/usr/local/lib/Alexander-<VERSION>.jar:$CLASSPATH"
```

It is critical to have the dot, the current directory identifier, somewhere in the `CLASSPATH`. Without that, the Java
compiler and JVM won't see classes in the arbitrary directory.

You can check to see that Alexander is installed correcty now by running the Alexander commands without arguments:

```
java -jar /usr/local/lib/Alexander-<VERSION>.jar
```

Typing the command above to run Alexander all the time would be painful. It is best to make an alias or shell script.
Starting from this point, I'll use alias `alexander`, which you can define as follows on Unix:

```
alias alexander='java -jar /usr/local/lib/Alexander-<VERSION>.jar'
```

Now you get to say just `alexander`.

```
$ alexander 
Alexander

Copyright Jiaiq Liu

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
------------------
Available commands
   extractColumn  A command that prints out a specific column from rows of data.
extractInterface  A command that generates a Java interface file from the methods in a Java class definition.
...
```

If you see the help message, then you are ready to use Alexander.

## Generate Java Interface File

Imagine your boss assigns you to build a tool that generates a Java interface file from the methods in a Java class
definition. Panic ensues if you are a junior programmer. As an experience Java developer, you might suggest using the
Java reflection API or the `javap` tool to extract method signatures. If your Java tool building kung fu is very strong,
you might even try using a bytecode library such as [ASM](http://asm.ow2.org). Then your boss says, "Oh yeah. Preserve
whitespace and comments within the bounds of the method signature." There is no way around it now. We have to parse Java
source code.

Believe it or not, you can solve the core of this problem in just one command. We are going to generate an interface
with the method signatures, preserving the whitespace and comments, from the following Java file called `Demo.java`:

```java
import java.util.List;
import java.util.Map;
public class Demo {
    void f(int x, String y) { }
    int[ ] g(/*no args*/) { return null; }
    List<Map<String, Integer>>[] h() { return null; }
}
```

Running

```
alexander extractInterface absolute/path/to/Demo.java
```

will print the following interface in std output:

```java
interface Demo {
    void f(int x, String y);
    int[ ] g(/*no args*/);
    List<Map<String, Integer>>[] h();
}
```

## Extract a Specific Column from Rows of Data

This comes up all the time when people would like to grab just a column from a text file, such as the name or email
column. For the demoing purpose, let's use the following data(`data.rows`);

```
parrt   Terence Parr    101
tombu   Tom Burns       020
bke     Kevin Edgar     008
```

**The columns must be tab-delimited** and each row ends with a new line character.

```
alexander extractColumn data.rows 1

parrt
tombu
bke
```

```
alexander extractColumn data.rows 2

Terence Parr
Tom Burns
Kevin Edgar
```

Note that index starts at 1. Space is allowed in column value.

## License
The use and distribution terms for this software are covered by the Apache License, Version 2.0 (
 http://www.apache.org/licenses/LICENSE-2.0.html ).
