package pl.lidkowiak.battleships.gamelogic;

interface GridSquare {

    ShotResult shot();

    State state();

}
