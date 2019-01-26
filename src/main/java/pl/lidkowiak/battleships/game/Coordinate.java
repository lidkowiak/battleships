package pl.lidkowiak.battleships.game;

import java.util.Objects;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.Objects.isNull;

class Coordinate {

    private static final Pattern COORDINATE_STRING_PATTERN = Pattern.compile("^([A-Za-z]) *([1-9][0-9]*)$");

    static Optional<Coordinate> parse(String toParse) {
        if (isNull(toParse)) {
            return Optional.empty();
        }
        final Matcher matcher = COORDINATE_STRING_PATTERN.matcher(toParse.trim());
        if (!matcher.matches()) {
            return Optional.empty();
        }
        final String columnString = matcher.group(1).toUpperCase();
        final String rowString = matcher.group(2);

        return Optional.of(new Coordinate(columnString.charAt(0), Integer.parseUnsignedInt(rowString)));
    }

    static Coordinate of(char column, int row) {
        return new Coordinate(column, row);
    }

    private final char column;
    private final int row;

    private Coordinate(char column, int row) {
        this.column = column;
        this.row = row;
    }

    int columnZeroIndexed() {
        return column - 'A';
    }

    int rowZeroIndexed() {
        return row - 1;
    }

    Coordinate rightNeighbour() {
        return new Coordinate((char) (column + 1), row);
    }

    Coordinate bottomNeighbour() {
        return new Coordinate(column, row + 1);
    }

    boolean isWithinSquaredBoardOfSize(int sizeToTest) {
        return isWithinRange(columnZeroIndexed(), 0, sizeToTest - 1)
                && isWithinRange(rowZeroIndexed(), 0, sizeToTest - 1);
    }

    private boolean isWithinRange(int toTest, int lowerBound, int upperBound) {
        return toTest >= lowerBound && toTest <= upperBound;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Coordinate)) {
            return false;
        }
        final Coordinate that = (Coordinate) o;

        return Objects.equals(column, that.column) && Objects.equals(row, that.row);
    }

    @Override
    public int hashCode() {
        return Objects.hash(column, row);
    }
}
