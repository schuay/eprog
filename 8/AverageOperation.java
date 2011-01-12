import java.util.Arrays;

public class AverageOperation extends FilterOperation {

    public AverageOperation(BlockGenerator generator) {
        super(generator);
    }

    public int filter(int[] values) {

        double sum = 0;

        for (int i : values)
            sum += (double)i;

        return (int)Math.round(sum / values.length);

    }

}
