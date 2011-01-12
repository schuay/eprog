import java.util.Arrays;

public class MedianOperation extends FilterOperation {

    public MedianOperation(BlockGenerator generator) {
        super(generator);
    }

    public int filter(int[] values) {

        /* sort and return median */
        Arrays.sort(values);
        return values[values.length / 2];

    }

}
