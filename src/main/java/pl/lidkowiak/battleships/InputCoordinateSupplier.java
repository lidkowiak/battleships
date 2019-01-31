package pl.lidkowiak.battleships;

import pl.lidkowiak.battleships.gamelogic.Coordinate;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Optional;
import java.util.Scanner;
import java.util.function.Supplier;

class InputCoordinateSupplier implements Supplier<Coordinate> {

    private final Scanner in;
    private final PrintStream out;

    InputCoordinateSupplier(InputStream in, PrintStream out) {
        this.in = new Scanner(in);
        this.out = out;
    }

    @Override
    public Coordinate get() {
        while (true) {
            out.print("Your shoot: ");
            final Optional<Coordinate> candidate = Coordinate.parse(in.nextLine());
            if (candidate.isPresent()) {
                return candidate.get();
            }
            out.println("Can not recognize coordinate. Valid coordinate example: A4");
        }
    }

}
