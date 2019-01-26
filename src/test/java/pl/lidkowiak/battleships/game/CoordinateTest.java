package pl.lidkowiak.battleships.game;

import org.junit.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class CoordinateTest {

    @Test
    public void should_parse_coordinates_from_proper_strings() {
        successfullyParseAndAssert("A5", 'A', 5);
        successfullyParseAndAssert("C10", 'C', 10);
        successfullyParseAndAssert(" B2 ", 'B', 2);
        successfullyParseAndAssert("j4", 'J', 4);
        successfullyParseAndAssert("D 7", 'D', 7);
        successfullyParseAndAssert("D    7", 'D', 7);
    }

    @Test
    public void should_not_be_able_to_parse() {
        assertNotAbleToParse("_A7");
        assertNotAbleToParse("A-7");
        assertNotAbleToParse("");
        assertNotAbleToParse(null);
        assertNotAbleToParse("Run, Forrest, Run!");
    }

    @Test
    public void should_properly_determine_right_neighbour() {
        assertThat(new Coordinate('A', 10).rightNeighbour())
                .isEqualTo(new Coordinate('B', 10));
    }

    @Test
    public void should_properly_determine_bottom_neighbour() {
        assertThat(new Coordinate('A', 10).bottomNeighbour())
                .isEqualTo(new Coordinate('A', 11));
    }

    private void successfullyParseAndAssert(String toParse, char expectedColumn, int expectedRow) {
        //given
        //when
        Optional<Coordinate> cut = Coordinate.parse(toParse);

        //then
        assertThat(cut)
                .isNotEmpty()
                .contains(new Coordinate(expectedColumn, expectedRow));
    }

    private void assertNotAbleToParse(String toParse) {
        //given
        //when
        Optional<Coordinate> cut = Coordinate.parse(toParse);

        //then
        assertThat(cut).isEmpty();
    }

}
