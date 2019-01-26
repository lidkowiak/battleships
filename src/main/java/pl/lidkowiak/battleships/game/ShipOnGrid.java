package pl.lidkowiak.battleships.game;

import lombok.Builder;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static java.util.Collections.unmodifiableMap;

class ShipOnGrid {

    private static class PieceSquare implements GridSquare {

        private final ShipOnGrid ship;
        private State state;

        private PieceSquare(ShipOnGrid ship) {
            this.ship = ship;
            this.state = State.NOT_HIT;
        }

        @Override
        public ShotResult shot() {
            state = State.HIT;
            return ship.hit();
        }

        @Override
        public State state() {
            return state;
        }

        private void shipSank() {
            state = State.SUNK;
        }

    }

    private final int size;
    private final Map<Coordinate, PieceSquare> pieces;
    private int hits;

    @Builder
    ShipOnGrid(int size, Coordinate startPosition, Orientation orientation) {
        this.size = size;
        this.pieces = new HashMap<>(size);
        this.hits = 0;

        initPieces(startPosition, orientation);
    }


    Map<Coordinate, GridSquare> getPieces() {
        return unmodifiableMap(pieces);
    }

    boolean overlap(ShipOnGrid second) {
        final Set<Coordinate> intersection = new HashSet<>(pieces.keySet());
        intersection.retainAll(second.pieces.keySet());
        return !intersection.isEmpty();
    }

    boolean isSunk() {
        return hits >= size;
    }

    private void initPieces(Coordinate startPosition, Orientation orientation) {
        Coordinate current = startPosition;
        for (int i = 1; i <= size; i++, current = orientation.next(current)) {
            pieces.put(current, new PieceSquare(this));
        }
    }

    private ShotResult hit() {
        hits++;
        if (isSunk()) {
            pieces.values().forEach(PieceSquare::shipSank);
            return ShotResult.SINK;
        }
        return ShotResult.HIT;
    }

}
