package pl.lidkowiak.battleships.game;

class EmptySquare implements GridSquare {

    private State state;

    EmptySquare() {
        state = State.NOT_HIT;
    }

    @Override
    public ShotResult shot() {
        state = State.MISS;
        return ShotResult.MISS;
    }

    @Override
    public State state() {
        return state;
    }
}
