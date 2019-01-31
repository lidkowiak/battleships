package pl.lidkowiak.battleships.gamelogic;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.UnaryOperator;

import static java.util.Collections.unmodifiableMap;
import static pl.lidkowiak.battleships.gamelogic.ShotResult.ALREADY_SHOT;
import static pl.lidkowiak.battleships.gamelogic.ShotResult.SINK;
import static pl.lidkowiak.battleships.gamelogic.State.*;

public class ShipOnGrid {

    private final Map<Coordinate, PieceSquare> pieces;
    private final ShipKind ship;
    private int hits;

    public ShipOnGrid(ShipKind shipKind, Coordinate startPosition, Orientation orientation) {
        this.ship = shipKind;
        this.pieces = new HashMap<>(shipKind.size());
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
        return hits >= ship.size();
    }

    private void initPieces(Coordinate startPosition, Orientation orientation) {
        Coordinate current = startPosition;
        for (int i = 1; i <=  ship.size(); i++, current = orientation.next(current)) {
            pieces.put(current, new PieceSquare(this));
        }
    }

    private ShotResult hit() {
        hits++;
        if (isSunk()) {
            pieces.values().forEach(PieceSquare::shipSank);
            return SINK;
        }
        return ShotResult.HIT;
    }

    public enum Orientation {

        /**
         * The horizontal (right <-> left) orientation
         */
        HORIZONTAL(Coordinate::rightNeighbour),

        /**
         * The vertical (top <-> bottom) orientation
         */
        VERTICAL(Coordinate::bottomNeighbour);

        private final UnaryOperator<Coordinate> nextFunction;

        Orientation(UnaryOperator<Coordinate> nextFunction) {
            this.nextFunction = nextFunction;
        }

        Coordinate next(Coordinate c) {
            return nextFunction.apply(c);
        }
    }

    private static class PieceSquare implements GridSquare {

        private final ShipOnGrid ship;
        private State state;

        private PieceSquare(ShipOnGrid ship) {
            this.ship = ship;
            this.state = NOT_HIT;
        }

        @Override
        public ShotResult shot() {
            if (NOT_HIT.equals(state)) {
                state = HIT;
                return ship.hit();
            }
            return ALREADY_SHOT;
        }

        @Override
        public State state() {
            return state;
        }

        private void shipSank() {
            state = SUNK;
        }

    }
}
