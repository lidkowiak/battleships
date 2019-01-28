package pl.lidkowiak.battleships.gamelogic;

import pl.lidkowiak.battleships.gamelogic.ShipOnGrid.Orientation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.Random;

import static pl.lidkowiak.battleships.gamelogic.ShipOnGrid.Orientation.HORIZONTAL;

class RandomShipOnGridDeployer {

    private static final int MAX_ATTEMPTS = 30;
    private static final int MAX_ATTEMPTS_PER_SHIP = 30;

    private final int boardSize;
    private final Ships[] ships;
    private final Collection<ShipOnGrid> shipsOnGrid;

    private final Random random;

    private final char maxColumn;

    RandomShipOnGridDeployer(int boardSize, Ships... ships) {
        this.boardSize = boardSize;
        this.ships = ships;
        this.shipsOnGrid = new ArrayList<>(ships.length);
        this.maxColumn = (char) ('A' + boardSize - 1);
        this.random = new Random(System.currentTimeMillis());
        placeAtRandom();
    }

    Collection<ShipOnGrid> shipsOnGrid() {
        return shipsOnGrid;
    }

    private void placeAtRandom() {
        for (int i = 0; i < MAX_ATTEMPTS; i++) {
            shipsOnGrid.clear();
            if (tryPlaceAtRandom()) {
                return;
            }
        }
        throw new IllegalStateException("Can not place ships at random!");
    }

    private boolean tryPlaceAtRandom() {
        for (Ships ship : ships) {
            if (!tryPlaceAtRandom(ship)) {
                return false;
            }
        }
        return true;
    }

    private boolean tryPlaceAtRandom(Ships ship) {
        if (ship.size() > boardSize) {
            throw new IllegalStateException("Ship " + ship + " is to big for given board!");
        }
        for (int i = 0; i < MAX_ATTEMPTS_PER_SHIP; i++) {
            final ShipOnGrid candidate = randomPlaceOnGridThatFitBoard(ship.size());
            if (!overlapWithAlreadyPlacedShips(candidate)) {
                shipsOnGrid.add(candidate);
                return true;
            }
        }
        return false;
    }

    private ShipOnGrid randomPlaceOnGridThatFitBoard(int shipSize) {
        final Orientation orientation = randomOrientation();
        final char maxCol = HORIZONTAL.equals(orientation) ? (char) (maxColumn - shipSize + 1) : maxColumn;
        final int maxRow = HORIZONTAL.equals(orientation) ? boardSize : boardSize - shipSize + 1;

        final char column = randomColumnInRange('A', maxCol);
        final int row = randomIntInRange(1, maxRow);

        return new ShipOnGrid(shipSize, Coordinate.of(column, row), orientation);
    }

    private boolean overlapWithAlreadyPlacedShips(ShipOnGrid candidate) {
        final Optional<ShipOnGrid> overlapWith = shipsOnGrid.stream()
                .filter(s -> s.overlap(candidate))
                .findAny();
        return overlapWith.isPresent();
    }

    private Orientation randomOrientation() {
        return Orientation.values()[randomIntInRange(0, 1)];
    }

    private int randomIntInRange(int min, int max) {
        return random.nextInt((max - min) + 1) + min;
    }

    private char randomColumnInRange(char min, char max) {
        return (char) (random.nextInt((max - min) + 1) + min);
    }

}
