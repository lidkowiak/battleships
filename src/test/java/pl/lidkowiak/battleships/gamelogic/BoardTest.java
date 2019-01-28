package pl.lidkowiak.battleships.gamelogic;

import org.junit.Test;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalStateException;
import static pl.lidkowiak.battleships.gamelogic.ShotResult.OUTSIDE_THE_GRID;

public class BoardTest {

    @Test
    public void should_not_be_able_to_create_board_with_ships_placed_outside_board() {
        assertThatIllegalStateException()
                .isThrownBy(() -> Board.newWithAlreadyPlacedShips(10,
                        singletonList(ShipOnGrid.builder()
                                .size(5)
                                .startPosition(Coordinate.of('G', 6))
                                .orientation(ShipOnGrid.Orientation.HORIZONTAL)
                                .build())
                        )
                )
                .withMessage("Ship is placed outside board.");
    }

    @Test
    public void should_not_be_able_to_create_board_with_overlapping_ships() {
        assertThatIllegalStateException()
                .isThrownBy(() -> Board.newWithAlreadyPlacedShips(10,
                        asList(ShipOnGrid.builder()
                                        .size(5)
                                        .startPosition(Coordinate.of('A', 1))
                                        .orientation(ShipOnGrid.Orientation.HORIZONTAL)
                                        .build(),
                                ShipOnGrid.builder()
                                        .size(5)
                                        .startPosition(Coordinate.of('B', 1))
                                        .orientation(ShipOnGrid.Orientation.VERTICAL)
                                        .build())
                        )
                )
                .withMessage("Ships overlap.");
    }

    @Test
    public void should_get_OUT_OF_GRID_when_shot_out_of_board_range() {
        //given
        Board cut = Board.newWithAlreadyPlacedShips(10, singletonList(ShipOnGrid.builder()
                        .size(5)
                        .startPosition(Coordinate.of('A', 1))
                        .orientation(ShipOnGrid.Orientation.HORIZONTAL)
                        .build()));

        //when
        //then
        assertThat(cut.shot(Coordinate.of('Z', 4))).isEqualTo(OUTSIDE_THE_GRID);
    }

    @Test
    public void should_sink_ship() {
        //given
        Board cut = Board.newWithAlreadyPlacedShips(10, singletonList(ShipOnGrid.builder()
                        .size(5)
                        .startPosition(Coordinate.of('A', 1))
                        .orientation(ShipOnGrid.Orientation.HORIZONTAL)
                        .build()));

        //when
        cut.shot(Coordinate.of('A', 2));//MISS

        cut.shot(Coordinate.of('A', 1));//HIT
        cut.shot(Coordinate.of('B', 1));//HIT
        cut.shot(Coordinate.of('C', 1));//HIT
        cut.shot(Coordinate.of('D', 1));//HIT
        cut.shot(Coordinate.of('E', 1));//HIT

        //then
        assertThat(cut.allShipsAreSunk()).isTrue();
    }
}