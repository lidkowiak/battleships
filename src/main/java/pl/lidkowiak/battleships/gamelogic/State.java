package pl.lidkowiak.battleships.gamelogic;

public enum State {

    NOT_HIT(' '),
    MISS('O'),
    HIT('x'),
    SUNK('X');

    private final char asChar;

    State(char asChar) {
        this.asChar = asChar;
    }

    public char asChar() {
        return asChar;
    }
}
