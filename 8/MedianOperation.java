import java.util.Arrays;

public class MedianOperation extends FilterOperation {

    public int filter(int[] values) {

        /* sort and return median */
        Arrays.sort(values);
        return charset.charAt(values[neighborCount / 2]);

    }

}
