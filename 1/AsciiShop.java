import java.util.Scanner;

class AsciiShop {

    public static void main(String[] args) {

        /* local vars */
        int h = 0, w = 0;
        String line;

        /* create our scanner reading from stdin */
        Scanner s = new Scanner(System.in);

        try {
            /* process stdin line by line until we reach EOF */
            while (s.hasNextLine()) {
                line = s.nextLine();
                /* if this is our first line, save input width */
                if (h == 0) {
                    w = line.length();
                /* otherwise make sure width stays constant. if it doesn't,
                 * throw an exception to bail out */
                } else if (line.length() != w) {
                    throw new Exception("INPUT MISMATCH");
                }
                /* increment our line counter */
                h++;
            }
            /* print results to stdout */
            System.out.printf("%d %d%n", w, h);
        } catch (Exception e) {
            /* on error, print message */
            System.out.println(e.getMessage());
        } finally {
            /* close our scanner */
            s.close();
        }
    }
}
