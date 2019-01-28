package pl.lidkowiak.battleships.gamelogic;

public enum Ships {

    BATTLESHIP(5),
    DESTROYER(4);

    private final int size;

    Ships(int size) {
        this.size = size;
    }

    int size() {
        return size;
    }

}
