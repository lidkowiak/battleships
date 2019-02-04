package pl.lidkowiak.battleships.gamelogic;

import java.util.Collection;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static pl.lidkowiak.battleships.gamelogic.ShotResult.*;
import static pl.lidkowiak.battleships.gamelogic.State.NOT_HIT;

public class Board {

    private final int size;
    private final GridSquare[][] grid;

    private int shipsToSinkCount;

    public static Board newWithShipsPlacedAtRandom(int size, ShipKind... ships) {
        return new Board(size, new RandomShipOnGridDeployer(size, ships).shipsOnGrid());
    }

    public static Board newWithAlreadyPlacedShips(int size, Collection<ShipOnGrid> ships) {
        return new Board(size, ships);
    }

    private Board(int size, Collection<ShipOnGrid> ships) {
        this.size = validateGreaterThanZero(size);
        this.grid = new GridSquare[size][size];
        this.shipsToSinkCount = ships.size();
        initGrid(validateNotEmpty(ships));
    }

    public ShotResult shot(Coordinate coordinate) {
        if (!coordinate.isWithinSquaredBoardOfSize(size)) {
            return OUTSIDE_THE_GRID;
        }
        final ShotResult shotResult = gridSquareAt(coordinate).shot();
        if (SINK.equals(shotResult)) {
            shipsToSinkCount--;
        }
        return shotResult;
    }

    public boolean allShipsAreSunk() {
        return shipsToSinkCount <= 0;
    }

    public int size() {
        return size;
    }

    public State[][] currentState() {
        final State[][] currentState = new State[size][size];

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

    private void initGrid(Collection<ShipOnGrid> ships) {
        ships.forEach(this::putShipOnGrid);
        fillInNullSquaresWithEmptySquare();
    }

    private void putShipOnGrid(ShipOnGrid shipOnGrid) {
        shipOnGrid.getPieces().forEach((c, gs) -> {
            if (!c.isWithinSquaredBoardOfSize(size)) {
                throw new IllegalStateException("Ship is placed outside board.");
            }
            if (nonNull(gridSquareAt(c))) {
                throw new IllegalStateException("ShipKind overlap.");
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
            state = NOT_HIT;
        }

        @Override
        public ShotResult shot() {
            if (NOT_HIT.equals(state)) {
                state = State.MISS;
                return MISS;
            }
            return ALREADY_SHOT;
        }

        @Override
        public State state() {
            return state;
        }
    }
}
