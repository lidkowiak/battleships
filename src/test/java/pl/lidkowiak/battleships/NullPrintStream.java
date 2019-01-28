package pl.lidkowiak.battleships;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

public class NullPrintStream extends PrintStream {

    public NullPrintStream() {
        super(new OutputStream() {

            @Override
            public void write(int b) throws IOException {
            }
        });
    }
}
