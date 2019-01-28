package pl.lidkowiak.battleships.gamelogic;

public enum State {

    NOT_HIT(' '),
    MISS('O'),
    HIT('X'),
    SUNK('S');

    private final char asChar;

    State(char asChar) {
        this.asChar = asChar;
    }

    public char asChar() {
        return asChar;
    }
}
