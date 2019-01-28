package pl.lidkowiak.battleships;

import pl.lidkowiak.battleships.gamelogic.Board;
import pl.lidkowiak.battleships.gamelogic.State;

import java.io.PrintStream;
import java.util.List;

class BoardPrinter {

    private final PrintStream out;
    private final Board board;

    private final String header;

    BoardPrinter(PrintStream out, Board board) {
        this.out = out;
        this.board = board;
        this.header = initHeader();
    }

    void print() {
        final StringBuilder sb = new StringBuilder();
        sb.append(header);

        for (int rowNo = 1; rowNo <= board.size(); rowNo++) {
            final List<State> row = board.row(rowNo);
            appendRow(sb, rowNo, row);
        }

        out.println(sb.toString());
    }

    private String initHeader() {
        final StringBuilder sb = new StringBuilder();

        sb.append("   ");
        for (char col = 'A'; col < 'A' + board.size(); col++) {
            sb.append(col + " ");
        }
        sb.append("\n");

        return sb.toString();
    }

    private void appendRow(StringBuilder sb, int rowNo, List<State> row) {
        sb.append(String.format("%2d ", rowNo));
        row.forEach(s -> sb.append(s.asChar() + " "));
        sb.append("\n");
    }
    
}
