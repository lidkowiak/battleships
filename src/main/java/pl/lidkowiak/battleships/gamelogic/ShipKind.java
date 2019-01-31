package pl.lidkowiak.battleships.gamelogic;

public enum ShipKind {

    BATTLESHIP(5),
    DESTROYER(4);

    private final int size;

    ShipKind(int size) {
        this.size = size;
    }

    int size() {
        return size;
    }

}
