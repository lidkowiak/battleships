package pl.lidkowiak.battleships.gamelogic;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalStateException;
import static pl.lidkowiak.battleships.gamelogic.ShipKind.BATTLESHIP;
import static pl.lidkowiak.battleships.gamelogic.ShipKind.DESTROYER;

public class RandomShipOnGridDeployerTest {

    @Test
    public void should_place_at_random_one_battleship_and_two_destroyers_on_10x10_board() {
        //given
        //when
        RandomShipOnGridDeployer cut = new RandomShipOnGridDeployer(10, BATTLESHIP, DESTROYER, DESTROYER);

        //then
        assertThat(cut.shipsOnGrid()).hasSize(3);
    }

    @Test
    public void should_throw_exception_when_there_is_ship_bigger_than_board() {
        assertThatIllegalStateException()
                .isThrownBy(() -> new RandomShipOnGridDeployer(2, BATTLESHIP));
    }

    @Test
    public void should_throw_exception_when_there_is_impossible_to_place_ships_on_board() {
        assertThatIllegalStateException()
                .isThrownBy(() -> new RandomShipOnGridDeployer(4,
                        DESTROYER, DESTROYER, DESTROYER, DESTROYER, DESTROYER));
    }
}