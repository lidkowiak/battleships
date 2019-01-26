package pl.lidkowiak.battleships.game;

import java.util.HashMap;
import java.util.Map;

import static java.util.Collections.unmodifiableMap;

class Ship {

    static class PieceSquare implements GridSquare {

        private final Ship ship;
        private State state;

        private PieceSquare(Ship ship) {
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

        void shipSank() {
            state = State.SUNK;
        }

    }

    private final int size;
    private Map<Coordinate, PieceSquare> pieces;
    private int hits;

    Ship(int size) {
        this.size = size;
        this.hits = 0;
    }

    void setPosition(Coordinate from, Orientation orientation) {
        pieces = new HashMap<>(size);
        Coordinate current = from;
        for (int i = 1; i <= size; i++, current = orientation.next(current)) {
            pieces.put(current, new PieceSquare(this));
        }
    }

    public Map<Coordinate, GridSquare> getPieces() {
        return unmodifiableMap(pieces);
    }

    private ShotResult hit() {
        hits++;
        if (isSunk()) {
            pieces.values().forEach(PieceSquare::shipSank);
            return ShotResult.SINK;
        }
        return ShotResult.HIT;
    }

    private boolean isSunk() {
        return hits >= size;
    }


}
