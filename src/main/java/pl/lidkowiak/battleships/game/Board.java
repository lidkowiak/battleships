package pl.lidkowiak.battleships.game;

import lombok.Builder;

import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static pl.lidkowiak.battleships.game.ShotResult.OUT_OF_GRID;
import static pl.lidkowiak.battleships.game.ShotResult.SINK;

class Board {

    private final int size;
    private final GridSquare[][] grid;
    private final List<ShipOnGrid> ships;

    private int sunkShipsCount = 0;

    @Builder
    Board(int size, List<ShipOnGrid> ships) {
        this.size = size;
        this.grid = new GridSquare[size][size];
        this.ships = new ArrayList<>(ships);
        initGrid();
    }

    ShotResult shot(Coordinate coordinate) {
        if(!coordinate.isWithinSquaredBoardOfSize(size)) {
            return OUT_OF_GRID;
        }
        final ShotResult shotResult = gridSquareAt(coordinate).shot();
        if (SINK.equals(shotResult)) {
            sunkShipsCount++;
        }
        return shotResult;
    }

    boolean allShipsAreSunk() {
        return sunkShipsCount >= ships.size();
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
            if (nonNull(grid[c.columnZeroIndexed()][c.rowZeroIndexed()])) {
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
        grid[c.columnZeroIndexed()][c.rowZeroIndexed()] = gs;
    }

    private GridSquare gridSquareAt(Coordinate c) {
        return grid[c.columnZeroIndexed()][c.rowZeroIndexed()];
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
