package pl.lidkowiak.battleships;

import java.io.OutputStream;
import java.io.PrintStream;

class NullPrintStream extends PrintStream {

    NullPrintStream() {
        super(new OutputStream() {
            @Override
            public void write(int b) {
            }
        });
    }
}
