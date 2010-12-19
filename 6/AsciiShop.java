import java.util.Scanner;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import java.util.ArrayList;
import java.util.Stack;
import java.util.EmptyStackException;

import java.util.HashMap;

class AsciiShop {
    private static Scanner scanner;
    private static AsciiImage img = null;
    private static Stack<AsciiImage> stack;

    public static void main(String[] args) {
        InputStream instream;
        stack = new Stack<AsciiImage>();

        try {
            /* create our scanner reading from file or stdin */
            instream = (args.length == 1) ? new FileInputStream(new File(args[0]))
                                          : System.in;
            scanner = new Scanner(instream);

            /* initial create command */
            if (scanner.hasNextLine()) {
                String[] tokens = scanner.nextLine().split(" ");
                if (tokens.length != 4 || !tokens[0].equals(AsciiConstants.cmdCreate)) {
                    throw new AsciiException(AsciiConstants.errInp);
                }
                img = new AsciiImage(parseInt(tokens[1]), parseInt(tokens[2]), tokens[3]);
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

    private static void handleCmd(String[] tokens)
                                  throws AsciiException, OperationException {
        /* empty input, error */
        if (tokens.length == 0) {
            throw new AsciiException(AsciiConstants.errInp);
        }

        /* save reference to old image to add to undo stack */
        AsciiImage oldImg = new AsciiImage(img);
        Operation op;

        /* handle all possible cmds: check for cmd call validity,
         * and execute cmd logic */
        String cmd = tokens[0];
        if (cmd.equals(AsciiConstants.cmdClear)) {
            if (tokens.length != 1) {
                throw new AsciiException(AsciiConstants.errInp);
            }
            op = new ClearOperation();
            img = op.execute(img);
        } else if (cmd.equals(AsciiConstants.cmdCreate)) {
            /* create may not be called more than once */
            throw new AsciiException(AsciiConstants.errCmd);
        } else if (cmd.equals(AsciiConstants.cmdLoad)) {
            if (tokens.length != 2) {
                throw new AsciiException(AsciiConstants.errInp);
            }
            String data = "";
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                if (line.equals(tokens[1])) {
                    break;
                }
                data += line + "\n";
            }
            op = new LoadOperation(data);
            img = op.execute(img);
        } else if (cmd.equals(AsciiConstants.cmdFilter)) {
            if (tokens.length != 2 || !tokens[1].equals("median")) {
                throw new AsciiException(AsciiConstants.errInp);
            }
            op = new MedianOperation();
            img = op.execute(img);
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
            op = new ReplaceOperation(tokens[1].charAt(0), tokens[2].charAt(0));
            img = op.execute(img);
        } else if (cmd.equals(AsciiConstants.cmdUndo)) {
            if (tokens.length != 1) {
                throw new AsciiException(AsciiConstants.errInp);
            }
            try {
                img = stack.pop();
            } catch (EmptyStackException ex) {
                System.out.println(AsciiConstants.errStackEmpty);
            }
        } else {
            throw new AsciiException(AsciiConstants.errCmd);
        }

        /* only keep history for destructive cmds */
        if (!cmd.equals(AsciiConstants.cmdUndo) &&
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
