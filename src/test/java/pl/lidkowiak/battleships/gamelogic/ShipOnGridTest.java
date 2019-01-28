package pl.lidkowiak.battleships.gamelogic;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static pl.lidkowiak.battleships.gamelogic.State.NOT_HIT;
import static pl.lidkowiak.battleships.gamelogic.State.SUNK;

public class ShipOnGridTest {

    @Test
    public void should_newly_created_ship_be_not_sunk_and_has_properly_set_pieces_with_NO_HIT_state() {
        // given
        //when
        ShipOnGrid cut = new ShipOnGrid(5, Coordinate.of('A', 1), Orientation.HORIZONTAL);

        Map<Coordinate, GridSquare> pieces = cut.getPieces();

        //then
        assertThat(cut.isSunk()).isFalse();
        assertThat(pieces.keySet()).containsExactlyInAnyOrder(
                Coordinate.of('A', 1),
                Coordinate.of('B', 1),
                Coordinate.of('C', 1),
                Coordinate.of('D', 1),
                Coordinate.of('E', 1)
        );

        pieces.values().forEach(p -> assertThat(p.state()).isEqualTo(NOT_HIT));
    }

    @Test
    public void should_ship_sunk_when_all_of_its_pieces_are_hit() {
        // given
        ShipOnGrid cut = new ShipOnGrid(3, Coordinate.of('A', 1), Orientation.HORIZONTAL);

        //when
        List<GridSquare> pieces = new ArrayList<>(cut.getPieces().values());

        assertThat(pieces.get(0).shot()).isEqualTo(ShotResult.HIT);
        assertThat(pieces.get(1).shot()).isEqualTo(ShotResult.HIT);
        assertThat(pieces.get(2).shot()).isEqualTo(ShotResult.SINK);

        //then
        assertThat(cut.isSunk()).isTrue();
        pieces.forEach(p -> assertThat(p.state()).isEqualTo(SUNK));
    }

    @Test
    public void should_not_skin_ship_when_not_all_of_its_pieces_are_hit() {
        // given
        ShipOnGrid cut = new ShipOnGrid(3, Coordinate.of('A', 1), Orientation.HORIZONTAL);

        //when
        List<GridSquare> pieces = new ArrayList<>(cut.getPieces().values());

        pieces.get(0).shot();
        pieces.get(0).shot();
        pieces.get(0).shot();

        //then
        assertThat(cut.isSunk()).isFalse();
    }

    @Test
    public void should_return_that_two_ships_overlap() {
        // given
        ShipOnGrid ship1 = new ShipOnGrid(3, Coordinate.of('A', 1), Orientation.HORIZONTAL);
        ShipOnGrid ship2 = new ShipOnGrid(4, Coordinate.of('B', 1), Orientation.VERTICAL);

        //when
        //then
        assertThat(ship1.overlap(ship2)).isTrue();
    }

    @Test
    public void should_return_that_two_ships_does_not_overlap() {
        // given
        ShipOnGrid ship1 = new ShipOnGrid(3, Coordinate.of('A', 1), Orientation.HORIZONTAL);
        ShipOnGrid ship2 = new ShipOnGrid(4, Coordinate.of('G', 5), Orientation.VERTICAL);

        //when
        //then
        assertThat(ship1.overlap(ship2)).isFalse();
    }
}