package pl.lidkowiak.battleships.game;

interface GridSquare {

    enum State {
        NOT_HIT, MISS, HIT, SUNK
    }

    ShotResult shot();

    State state();

}
