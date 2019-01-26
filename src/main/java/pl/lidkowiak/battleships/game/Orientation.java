package pl.lidkowiak.battleships.game;

import java.util.function.UnaryOperator;

enum Orientation {

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
