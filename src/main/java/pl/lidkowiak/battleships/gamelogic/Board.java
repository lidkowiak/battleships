package pl.lidkowiak.battleships.gamelogic;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static pl.lidkowiak.battleships.gamelogic.ShotResult.OUTSIDE_THE_GRID;
import static pl.lidkowiak.battleships.gamelogic.ShotResult.SINK;

public class Board {

    private final int size;
    private final GridSquare[][] grid;
    private final List<ShipOnGrid> ships;

    private int sunkShipsCount = 0;

    public static Board newWithShipsPlacedAtRandom(int size, Ships... ships) {
        return newWithAlreadyPlacedShips(size, new RandomShipOnGridDeployer(size, ships).shipsOnGrid());
    }

    public static Board newWithAlreadyPlacedShips(int size, Collection<ShipOnGrid> ships) {
        return new Board(size, ships);
    }

    private Board(int size, Collection<ShipOnGrid> ships) {
        this.size = validateGreaterThanZero(size);
        this.grid = new GridSquare[size][size];
        this.ships = new ArrayList<>(validateNotEmpty(ships));
        initGrid();
    }

    public ShotResult shot(Coordinate coordinate) {
        if (!coordinate.isWithinSquaredBoardOfSize(size)) {
            return OUTSIDE_THE_GRID;
        }
        final ShotResult shotResult = gridSquareAt(coordinate).shot();
        if (SINK.equals(shotResult)) {
            sunkShipsCount++;
        }
        return shotResult;
    }

    public boolean allShipsAreSunk() {
        return sunkShipsCount >= ships.size();
    }

    public int size() {
        return size;
    }

    public State[][] curentState() {
        State[][] currentState = new State[size][size];

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                currentState[i][j] = grid[i][j].state();
            }
        }
        return currentState;
    }

    private int validateGreaterThanZero(int size) {
        if (size <= 0) {
            throw new IllegalArgumentException("Board size is less or equal 0!");
        }
        return size;
    }

    private Collection<ShipOnGrid> validateNotEmpty(Collection<ShipOnGrid> ships) {
        if (ships.isEmpty()) {
            throw new IllegalArgumentException("There is no ship!");
        }
        return ships;
    }

    private void initGrid() {
        ships.forEach(this::putShipOnGrid);
        fillInNullSquaresWithEmptySquare();
    }

    private void putShipOnGrid(ShipOnGrid shipOnGrid) {
        shipOnGrid.getPieces().forEach((c, gs) -> {
            if (!c.isWithinSquaredBoardOfSize(size)) {
                throw new IllegalStateException("Ship is placed outside board.");
            }
            if (nonNull(gridSquareAt(c))) {
                throw new IllegalStateException("Ships overlap.");
            }
            setGridSquareAt(c, gs);
        });
    }

    private void fillInNullSquaresWithEmptySquare() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (isNull(grid[i][j])) {
                    grid[i][j] = new EmptySquare();
                }
            }
        }
    }

    private void setGridSquareAt(Coordinate c, GridSquare gs) {
        grid[c.rowZeroIndexed()][c.columnZeroIndexed()] = gs;
    }

    private GridSquare gridSquareAt(Coordinate c) {
        return grid[c.rowZeroIndexed()][c.columnZeroIndexed()];
    }

    private static class EmptySquare implements GridSquare {

        private State state;

        EmptySquare() {
            state = State.NOT_HIT;
        }

        @Override
        public ShotResult shot() {
            state = State.MISS;
            return ShotResult.MISS;
        }

        @Override
        public State state() {
            return state;
        }
    }
}
