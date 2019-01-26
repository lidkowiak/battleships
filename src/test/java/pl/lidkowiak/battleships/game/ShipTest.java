package pl.lidkowiak.battleships.game;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static pl.lidkowiak.battleships.game.GridSquare.State.NOT_HIT;
import static pl.lidkowiak.battleships.game.GridSquare.State.SUNK;

public class ShipTest {

    @Test
    public void should_properly_set_position_with_horizontal_occupation_of_grip_squares() {
        // given
        Ship cut = new Ship(5);

        //when
        cut.setPosition(new Coordinate('A', 1), Orientation.HORIZONTAL);
        Map<Coordinate, GridSquare> pieces = cut.getPieces();

        //then
        assertThat(pieces.keySet()).containsExactlyInAnyOrder(
                new Coordinate('A', 1),
                new Coordinate('B', 1),
                new Coordinate('C', 1),
                new Coordinate('D', 1),
                new Coordinate('E', 1)
        );

        pieces.values().forEach(p -> assertThat(p.state()).isEqualTo(NOT_HIT));
    }

    @Test
    public void should_properly_set_position_with_vertical_occupation_of_grip_squares() {
        // given
        Ship cut = new Ship(4);

        //when
        cut.setPosition(new Coordinate('A', 1), Orientation.VERTICAL);

        //then
        assertThat(cut.getPieces().keySet()).containsExactlyInAnyOrder(
                new Coordinate('A', 1),
                new Coordinate('A', 2),
                new Coordinate('A', 3),
                new Coordinate('A', 4)
        );
    }

    @Test
    public void should_ship_sunk_when_all_of_its_pieces_are_hit() {
        // given
        Ship cut = new Ship(3);

        cut.setPosition(new Coordinate('A', 1), Orientation.HORIZONTAL);

        List<GridSquare> pieces = new ArrayList<>(cut.getPieces().values());

        //when
        //then
        assertThat(pieces.get(0).shot()).isEqualTo(ShotResult.HIT);
        assertThat(pieces.get(1).shot()).isEqualTo(ShotResult.HIT);
        assertThat(pieces.get(2).shot()).isEqualTo(ShotResult.SINK);

        pieces.forEach(p -> assertThat(p.state()).isEqualTo(SUNK));
    }
}