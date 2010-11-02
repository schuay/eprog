import java.util.Scanner;

class AsciiShop {
    private static final String
        cread = "read",
        cunique = "uniqueChars",
        cflip = "flip-v",
        ctranspose = "transpose",
        csymmetric = "symmetric-h",
        errstr_inp = "INPUT MISMATCH";

    public static void main(String[] args) {

        /* local vars */
        int linecount;
        String line;
        AsciiImage img = new AsciiImage();

        /* create our scanner reading from stdin */
        Scanner s = new Scanner(System.in);

        try {
            /* process 'read' instruction */
            if (s.hasNextLine()) {
                line = s.nextLine();
                /* parse and validate. invalid if not in exactly:
                 * read <number> */
                String[] tokens = line.split(" ");
                if (tokens.length != 2 ||
                    !tokens[0].equals(cread)) {
                    throw new Exception(errstr_inp);
                }
                try {
                    linecount = Integer.parseInt(tokens[1].trim());
                } catch (NumberFormatException e) {
                    throw new Exception(errstr_inp);
                }
            } else {
                /* empty (= invalid) input */
                throw new Exception(errstr_inp);
            }
            /* process stdin line by line until we reach EOF */
            while (s.hasNextLine()) {
                line = s.nextLine();
                if (!img.addLine(line)) {
                    throw new Exception(errstr_inp);
                }
                /* check for abort conditions */
                if (img.getHeight() == linecount) {
                    break;
                }
            }
            /* input format check */
            if (img.getHeight() != linecount) {
                throw new Exception(errstr_inp);
            }
            /* process command (if present) */
            if (s.hasNextLine()) {
                line = s.nextLine();
                String[] tokens = line.split(" ");
                if (tokens.length != 1) {
                    throw new Exception(errstr_inp);
                }
                if (tokens[0].equals(cunique)) {
                    System.out.printf("%d%n", img.getUniqueChars());
                } else if (tokens[0].equals(cflip)) {
                    img.flipV();
                } else if (tokens[0].equals(ctranspose)) {
                    img.transpose();
                } else if (tokens[0].equals(csymmetric)) {
                    System.out.printf("%s%n", img.isSymmetric());
                } else {
                    throw new Exception(errstr_inp);
                }
            }
            /* print results to stdout */
            System.out.printf("%s%n", img);
            System.out.printf("%d %d%n", img.getWidth(), img.getHeight());
        } catch (Exception e) {
            /* on error, print message */
            System.out.println(e.getMessage());
        } finally {
            /* close our scanner */
            s.close();
        }
    }
}
