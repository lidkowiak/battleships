package pl.lidkowiak.battleships;

import pl.lidkowiak.battleships.gamelogic.Board;
import pl.lidkowiak.battleships.gamelogic.Coordinate;
import pl.lidkowiak.battleships.gamelogic.ShotResult;

import java.io.PrintStream;
import java.util.function.Supplier;

import static pl.lidkowiak.battleships.gamelogic.Ships.BATTLESHIP;
import static pl.lidkowiak.battleships.gamelogic.Ships.DESTROYER;

public class ConsoleBattleships {

    private static final int BOARD_SIZE = 10;

    private static final String LEGEND = "Play a one-sided game of Battleships.\n" +
            "Your goal is to sink one Battleship and two Destroyers.\n" +
            "Please enter coordinates of the form 'A5', where 'A' is the column and '5' is the row, to specify a square to target.\n" +
            "Grid legend: \n" +
            "'O' - miss, \n" +
            "'X' - hit, \n" +
            "'S' - part of sunk ship\n\n";


    public static void main(String[] args) {
        final ConsoleBattleships consoleBattleships = new ConsoleBattleships(
                Board.newWithShipsPlacedAtRandom(BOARD_SIZE, BATTLESHIP, DESTROYER, DESTROYER),
                new InputStreamCoordinateSupplier(System.in, System.out), System.out);
        consoleBattleships.play();
    }

    private final Board board;
    private final Supplier<Coordinate> coordinateSupplier;

    private final PrintStream out;
    private final String boardHeader;

    ConsoleBattleships(Board board, Supplier<Coordinate> coordinateSupplier, PrintStream out) {
        this.board = board;
        this.coordinateSupplier = coordinateSupplier;
        this.out = out;
        this.boardHeader = initBoardHeader();
    }

    void play() {
        out.println(LEGEND);

        printBoard();
        while (true) {
            final Coordinate coordinate = coordinateSupplier.get();
            final ShotResult shotResult = board.shot(coordinate);
            out.println(shotResult.message() + "\n");
            printBoard();
            if (board.allShipsAreSunk()) {
                break;
            }
        }
        out.println("All ships are sunk! GAME OVER");
    }

    private void printBoard() {
        final StringBuilder sb = new StringBuilder(boardHeader);

        for (int rowNo = 1; rowNo <= board.size(); rowNo++) {
            sb.append(String.format("%2d ", rowNo));
            board.row(rowNo)
                    .forEach(s -> sb.append(s.asChar() + " "));
            sb.append("\n");
        }

        out.println(sb.toString());
    }

    private String initBoardHeader() {
        final StringBuilder sb = new StringBuilder("   ");

        for (char col = 'A'; col < 'A' + board.size(); col++) {
            sb.append(col + " ");
        }
        sb.append("\n");

        return sb.toString();
    }

}
