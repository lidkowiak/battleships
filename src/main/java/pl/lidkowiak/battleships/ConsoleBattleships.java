package pl.lidkowiak.battleships;

import pl.lidkowiak.battleships.gamelogic.Board;
import pl.lidkowiak.battleships.gamelogic.Coordinate;
import pl.lidkowiak.battleships.gamelogic.ShotResult;
import pl.lidkowiak.battleships.gamelogic.State;

import java.io.PrintStream;
import java.util.function.Supplier;

import static pl.lidkowiak.battleships.gamelogic.ShipKind.BATTLESHIP;
import static pl.lidkowiak.battleships.gamelogic.ShipKind.DESTROYER;

public class ConsoleBattleships {

    private static final int BOARD_SIZE = 10;

    private static final String LEGEND = "Play a one-sided game of Battleships.\n" +
            "Your goal is to sink one Battleship and two Destroyers.\n" +
            "Please enter coordinates of the form 'A5', where 'A' is the column and '5' is the row, to specify a square to target.\n" +
            "Grid legend: \n" +
            "'O' - miss, \n" +
            "'x' - hit, \n" +
            "'X' - part of sunk ship\n";


    public static void main(String[] args) {
        final ConsoleBattleships consoleBattleships = new ConsoleBattleships(
                Board.newWithShipsPlacedAtRandom(BOARD_SIZE, BATTLESHIP, DESTROYER, DESTROYER),
                new InputCoordinateSupplier(System.in, System.out), System.out);
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
        this.boardHeader = boardHeader();
    }

    void play() {
        out.println(LEGEND);

        printBoard();
        while (true) {
            final Coordinate coordinate = coordinateSupplier.get();
            final ShotResult shotResult = board.shot(coordinate);
            out.println(shotResult.message());

            printBoard();
            if (board.allShipsAreSunk()) {
                out.println("All ships are sunk! GAME OVER");
                return;
            }
        }
    }

    private void printBoard() {
        final StringBuilder sb = new StringBuilder("\n").append(boardHeader);
        final State[][] states = board.currentState();

        for (int rowNo = 0; rowNo < states.length; rowNo++) {
            sb.append(String.format("%2d ", rowNo + 1));
            for (int colNo = 0; colNo < states[rowNo].length; colNo++) {
                final State stateAtSquare = states[rowNo][colNo];
                sb.append(stateAtSquare.asChar()).append(" ");
            }
            sb.append("\n");
        }

        out.println(sb.toString());
    }

    private String boardHeader() {
        final StringBuilder sb = new StringBuilder("   ");

        for (char col = 'A'; col < 'A' + board.size(); col++) {
            sb.append(col).append(" ");
        }

        return sb.append("\n").toString();
    }

}
