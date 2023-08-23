public interface ExtractInterfaceCommand {
    int run(final InputStream in, final PrintStream out, final PrintStream err, final List<String> args);
    void printHelp(final PrintStream out);
}
