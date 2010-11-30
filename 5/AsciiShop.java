import java.util.Scanner;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import java.util.ArrayList;

class AsciiShop {
    private static Scanner scanner;
    private static AsciiImage img = null;
    private static AsciiStack stack;

    public static void main(String[] args) {
        InputStream instream;
        stack = new AsciiStack(3);

        try {
            /* create our scanner reading from file or stdin */
            instream = (args.length == 1) ? new FileInputStream(new File(args[0])) : System.in;
            scanner = new Scanner(instream);

            /* initial create command */
            if (scanner.hasNextLine()) {
                String[] tokens = scanner.nextLine().split(" ");
                if (tokens.length != 3 || !tokens[0].equals(AsciiConstants.cmdCreate)) {
                    throw new AsciiException(AsciiConstants.errInp);
                }
                img = new AsciiImage(parseInt(tokens[1]), parseInt(tokens[2]));
            } else {
                throw new AsciiException(AsciiConstants.errInp);
            }
            /* main loop, all logic goes in here */
            while (scanner.hasNextLine()) {
                handleCmd(scanner.nextLine().split(" "));
            }
        } catch (Exception e) {
            /* on error, print message */
            System.out.println(e.getMessage());
        } finally {
            /* close used resources */
            scanner.close();
        }
    }
    private static void handleCmd(String[] tokens) throws AsciiException {
        /* empty input, error */
        if (tokens.length == 0) {
            throw new AsciiException(AsciiConstants.errInp);
        }

        AsciiImage oldImg = new AsciiImage(img);

        /* handle all possible cmds: check for cmd call validity,
         * and execute cmd logic */
        String cmd = tokens[0];
        if (cmd.equals(AsciiConstants.cmdClear)) {
            if (tokens.length != 1) {
                throw new AsciiException(AsciiConstants.errInp);
            }
            img.clear();
        } else if (cmd.equals(AsciiConstants.cmdCreate)) {
            /* create may not be called more than once */
            throw new AsciiException(AsciiConstants.errCmd);
        } else if (cmd.equals(AsciiConstants.cmdLine)) {
            if (tokens.length != 6) {
                throw new AsciiException(AsciiConstants.errInp);
            }
            img.drawLine(parseInt(tokens[1]), parseInt(tokens[2]),
                    parseInt(tokens[3]), parseInt(tokens[4]), tokens[5].charAt(0));
        } else if (cmd.equals(AsciiConstants.cmdLoad)) {
            if (tokens.length != 2) {
                throw new AsciiException(AsciiConstants.errInp);
            }
            ArrayList<String> data = new ArrayList<String>();
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                if (line.equals(tokens[1])) {
                    break;
                }
                data.add(line);
            }
            String imgarray[] = new String[data.size()];
            img.load(data.toArray(imgarray));
        } else if (cmd.equals(AsciiConstants.cmdPrint)) {
            if (tokens.length != 1) {
                throw new AsciiException(AsciiConstants.errInp);
            }
            System.out.print(img.toString());
            System.out.println();
        } else if (cmd.equals(AsciiConstants.cmdReplace)) {
            if (tokens.length != 3) {
                throw new AsciiException(AsciiConstants.errInp);
            }
            img.replace(tokens[1].charAt(0), tokens[2].charAt(0));
        } else if (cmd.equals(AsciiConstants.cmdUnique)) {
            if (tokens.length != 1) {
                throw new AsciiException(AsciiConstants.errInp);
            }
            System.out.println(img.getUniqueChars());
        } else if (cmd.equals(AsciiConstants.cmdCentroid)) {
            if (tokens.length != 2) {
                throw new AsciiException(AsciiConstants.errInp);
            }
            AsciiPoint p = img.getCentroid(tokens[1].charAt(0));
            System.out.println(p.toString());
        } else if (cmd.equals(AsciiConstants.cmdFlip)) {
            if (tokens.length != 1) {
                throw new AsciiException(AsciiConstants.errInp);
            }
            img.flipV();
        } else if (cmd.equals(AsciiConstants.cmdTranspose)) {
            if (tokens.length != 1) {
                throw new AsciiException(AsciiConstants.errInp);
            }
            img.transpose();
        } else if (cmd.equals(AsciiConstants.cmdSymmetric)) {
            if (tokens.length != 1) {
                throw new AsciiException(AsciiConstants.errInp);
            }
            System.out.println(img.isSymmetric());
        } else if (cmd.equals(AsciiConstants.cmdStraighten)) {
            if (tokens.length != 2) {
                throw new AsciiException(AsciiConstants.errInp);
            }
            img.straightenRegion(tokens[1].charAt(0));
        } else if (cmd.equals(AsciiConstants.cmdGrow)) {
            if (tokens.length != 2) {
                throw new AsciiException(AsciiConstants.errInp);
            }
            img.growRegion(tokens[1].charAt(0));
        } else if (cmd.equals(AsciiConstants.cmdUndo)) {
            if (tokens.length != 1) {
                throw new AsciiException(AsciiConstants.errInp);
            }
            AsciiImage newImg = stack.pop();
            if (newImg == null) {
                throw new AsciiException(AsciiConstants.errStackEmpty);
            } else {
                img = newImg;
                System.out.printf("STACK USAGE %d/%d%n", stack.size(), stack.capacity());
            }
        } else {
            throw new AsciiException(AsciiConstants.errCmd);
        }

        /* only keep history for destructive cmds */
        if (!cmd.equals(AsciiConstants.cmdUndo) &&
            !cmd.equals(AsciiConstants.cmdCentroid) &&
            !cmd.equals(AsciiConstants.cmdSymmetric) &&
            !cmd.equals(AsciiConstants.cmdUnique) &&
            !cmd.equals(AsciiConstants.cmdPrint))
            stack.push(oldImg);
    }
    private static int parseInt(String s) throws AsciiException {
        /* safe int parsing */
        try {
            return Integer.parseInt(s.trim());
        } catch (NumberFormatException e) {
            throw new AsciiException(AsciiConstants.errInp);
        }
    }
}
