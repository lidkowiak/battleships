package pl.lidkowiak.battleships.gamelogic;

import java.util.List;

import static java.util.Collections.singletonList;

public class RandomShipPlaceMaker {

    private final int boardSize;
    private final Ships[] ships;

    RandomShipPlaceMaker(int boardSize, Ships... ships) {
        this.boardSize = boardSize;
        this.ships = ships;
    }

    List<ShipOnGrid> shipsOnGrid() {
        return singletonList(ShipOnGrid.builder()
                .size(5)
                .startPosition(Coordinate.of('A', 1))
                .orientation(Orientation.HORIZONTAL)
                .build());
    }

}
