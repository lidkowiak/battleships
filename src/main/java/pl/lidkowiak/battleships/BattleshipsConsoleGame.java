package pl.lidkowiak.battleships;

import pl.lidkowiak.battleships.gamelogic.*;

import java.io.PrintStream;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class BattleshipsConsoleGame {

    private static final int BOARD_SIZE = 10;

    private final Board board;
    private final Scanner in;
    private final PrintStream out;
    private final BoardConsolePrinter boardPrinter;

    public static void main(String[] args) {
        new BattleshipsConsoleGame().play();
    }

    private BattleshipsConsoleGame() {
        this.board = Board.newWithShipsPlacedAtRandom(BOARD_SIZE, Ships.BATTLESHIP, Ships.DESTROYER, Ships.DESTROYER);
        this.in = new Scanner(System.in);
        this.out = System.out;
        this.boardPrinter = new BoardConsolePrinter(board, out);
    }

    private void play() {
        while (!board.allShipsAreSunk()) {
            boardPrinter.print();
            final ShotResult shotResult = board.shot(validCoordinateFromConsole());
        }
        printEndOfGameMessage();
    }

    private Coordinate validCoordinateFromConsole() {
        Optional<Coordinate> candidate = Optional.empty();
        out.println("Your shoot: ");
        while (!candidate.isPresent()) {
            candidate = Coordinate.parse(in.nextLine());
        }
        return candidate.get();
    }

    private void printEndOfGameMessage() {
        // potrzeba było tyle strzałów
    }

    private static class BoardConsolePrinter {

        private final Board board;
        private final String header;
        private final PrintStream out;

        public BoardConsolePrinter(Board board, PrintStream out) {
            this.board = board;
            this.header = initHeader();
            this.out = out;
        }

        private String initHeader() {
            final StringBuilder sb = new StringBuilder();
            sb.append("   ");
            for (char col = 'A'; col < 'A' + board.size(); col++) {
                sb.append(col);
            }
            sb.append("\n");

            return sb.toString();
        }

        public void print() {
            final StringBuilder sb = new StringBuilder();
            sb.append(header);

            for (int rowNo = 1; rowNo <= board.size(); rowNo++) {
                List<State> row = board.row(rowNo);
                appendRow(sb, rowNo, row);
            }
            out.println(sb.toString());
        }

        private void appendRow(StringBuilder sb, int rowNo, List<State> row) {
            sb.append(String.format("%2d ", rowNo));
            row.forEach(s -> sb.append(s.asChar()));
            sb.append("\n");
        }
    }
}
