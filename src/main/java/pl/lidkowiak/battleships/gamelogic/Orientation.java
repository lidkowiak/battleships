package pl.lidkowiak.battleships.gamelogic;

import java.util.function.UnaryOperator;

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
