package pl.lidkowiak.battleships.gamelogic;

public enum ShotResult {

    OUTSIDE_THE_GRID("Outside the grid!"),
    HIT("HIT!"),
    MISS("MISS"),
    SINK("SINK!"),
    ALREADY_SHOT("Already shot");

    private final String message;

    ShotResult(String message) {
        this.message = message;
    }

    public String message() {
        return message;
    }

}
