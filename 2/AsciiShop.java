import java.util.Scanner;

class AsciiShop {

    public static void main(String[] args) {

        /* local vars */
        int h = 0, w = 0;
        int linecount;
        String line;
        String key = "";
        String data = "";
        String cmd_read = "read";
        String cmd_decode = "decode";
        String errstr_inp = "INPUT MISMATCH";
        String errstr_key = "INVALID KEY";

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
                    !tokens[0].equals(cmd_read)) {
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
                /* if this is our first line, save input width */
                if (h == 0) {
                    w = line.length();
                /* otherwise make sure width stays constant. if it doesn't,
                 * throw an exception to bail out */
                } else if (line.length() != w) {
                    throw new Exception(errstr_inp);
                }
                /* increment our line counter */
                h++;
                /* save for decoding (newlines stripped by Scanner.nextLine */
                data += line;
                /* check for abort conditions */
                if (h == linecount) {
                    break;
                }
            }
            /* input format check */
            if (h != linecount) {
                throw new Exception(errstr_inp);
            }
            /* optionally decode picture */
            if (s.hasNextLine()) {
                line = s.nextLine();
                /* process 'decode' instruction */
                String[] tokens = line.split(" ");
                /* cmd invalid if not exactly:
                 * decode <key>
                 * if 'decode' not present, assume picture data -> INPUT err
                 * otherwise KEY err */
                if (tokens.length == 0 ||
                    !tokens[0].equals(cmd_decode)) {
                    throw new Exception(errstr_inp);
                }
                if (tokens.length != 2) {
                    throw new Exception(errstr_key);
                }
                key = tokens[1];

                /* validate key:
                 * 1) data length is multiple of key length
                 * 2) key contains all nrs 0 -> key length - 1 */
                if (data.length() % key.length() != 0) {
                    throw new Exception(errstr_key);
                }
                for (int i = 0; i < key.length(); i++) {
                    if (key.indexOf(Character.forDigit(i, 10)) == -1) {
                        throw new Exception(errstr_key);
                    }
                }
                /* decode in a nested loop by permuting original data */
                String encodeddata = data;
                data = "";
                for (int i = 0; i < encodeddata.length() / key.length(); i++) {
                    for (int j = 0; j < key.length(); j++) {
                        int offset = key.charAt(j) - '0';
                        data += encodeddata.charAt(i * key.length() + offset);
                    }
                }
            }
            /* print results to stdout:
             * 1) rearrange (decoded) data into individual lines
             * 2) print picture dimensions */
            for (int i = 0; i < h; i++) {
                System.out.printf("%s%n", data.substring(i * w, i * w + w));
            }
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
