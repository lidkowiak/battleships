package pl.lidkowiak.battleships;

import org.junit.Test;
import pl.lidkowiak.battleships.gamelogic.Board;
import pl.lidkowiak.battleships.gamelogic.Coordinate;
import pl.lidkowiak.battleships.gamelogic.ShipOnGrid;

import java.util.Iterator;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static pl.lidkowiak.battleships.gamelogic.ShipOnGrid.Orientation.HORIZONTAL;

public class ConsoleBattleshipsTest {

    @Test
    public void should_finish_game_when_all_ships_are_sunk() {
        //given
        Iterator<Coordinate> coordinateIterator = asList(Coordinate.of('A', 1),
                Coordinate.of('B', 1),
                Coordinate.of('C', 1),
                Coordinate.of('D', 1),
                Coordinate.of('E', 1)).iterator();

        Board board = Board.newWithAlreadyPlacedShips(10,
                singletonList(new ShipOnGrid(5, Coordinate.of('A', 1), HORIZONTAL))
        );
        ConsoleBattleships cut = new ConsoleBattleships(board, coordinateIterator::next, new NullPrintStream());

        //when
        cut.play();

        //then
        //game is finished!
    }
}