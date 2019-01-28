package pl.lidkowiak.battleships;

import pl.lidkowiak.battleships.gamelogic.Board;
import pl.lidkowiak.battleships.gamelogic.Coordinate;
import pl.lidkowiak.battleships.gamelogic.Ships;
import pl.lidkowiak.battleships.gamelogic.ShotResult;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Optional;
import java.util.Scanner;

public class BattleshipsConsoleGame {

    private static final int BOARD_SIZE = 10;

    private final Board board;
    private final Scanner in;
    private final PrintStream out;
    private final BoardPrinter boardPrinter;

    public static void main(String[] args) {
        final BattleshipsConsoleGame battleshipsConsoleGame = new BattleshipsConsoleGame(
                Board.newWithShipsPlacedAtRandom(BOARD_SIZE, Ships.BATTLESHIP, Ships.DESTROYER, Ships.DESTROYER),
                System.in, System.out);
        battleshipsConsoleGame.play();
    }

    private BattleshipsConsoleGame(Board board, InputStream in, PrintStream out) {
        this.board = board;
        this.in = new Scanner(in);
        this.out = out;
        this.boardPrinter = new BoardPrinter(out, board);
    }

    private void play() {
        boardPrinter.print();
        while (true) {
            final ShotResult shotResult = board.shot(coordinateFromIn());
            out.println(shotResult.message());
            boardPrinter.print();
            if (board.allShipsAreSunk()) {
                break;
            }
        }
        out.println("All ships are sunk! GAME OVER");
    }

    private Coordinate coordinateFromIn() {
        while (true) {
            out.print("Your shoot: ");
            final Optional<Coordinate> candidate = Coordinate.parse(in.nextLine());
            if (candidate.isPresent()) {
                return candidate.get();
            }
            out.println("Can not recognize coordinate. Valid coordinate example: A4");
        }
    }
}
