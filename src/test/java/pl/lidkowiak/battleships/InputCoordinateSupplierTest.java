package pl.lidkowiak.battleships;

import org.junit.Test;
import pl.lidkowiak.battleships.gamelogic.Coordinate;

import java.io.ByteArrayInputStream;

import static org.assertj.core.api.Assertions.assertThat;


public class InputCoordinateSupplierTest {

    @Test
    public void should_return_coordinate_when_valid_string_occurs() {
        //given
        ByteArrayInputStream in = new ByteArrayInputStream("XX\n123\nB10".getBytes());
        InputCoordinateSupplier cut = new InputCoordinateSupplier(in, new NullPrintStream());

        //when
        Coordinate coordinate = cut.get();

        //then
        assertThat(coordinate).isEqualTo(Coordinate.of('B', 10));
    }
}