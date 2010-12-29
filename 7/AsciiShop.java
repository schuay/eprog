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
    private static HashMap<String, Factory> cmds;

    public static void main(String[] args) {
        InputStream instream;
        stack = new Stack<AsciiImage>();
        initCommands();

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
                handleCmd(scanner);
            }
        } catch (Exception e) {
            /* on error, print message */
            System.out.println(e.getMessage());
        } finally {
            /* close used resources */
            scanner.close();
        }
    }

    private static void initCommands() {
        cmds = new HashMap<String, Factory>();

        cmds.put(AsciiConstants.cmdClear, new ClearFactory());
        cmds.put(AsciiConstants.cmdBinary, new BinaryFactory());
        cmds.put(AsciiConstants.cmdFilter, new FilterFactory());
        cmds.put(AsciiConstants.cmdLoad, new LoadFactory());
        cmds.put(AsciiConstants.cmdReplace, new ReplaceFactory());
    }

    private static void handleCmd(Scanner scanner)
        throws AsciiException, OperationException, FactoryException {

        /* save reference to old image to add to undo stack */
        AsciiImage oldImg = new AsciiImage(img);
        Operation op;

        String cmd = scanner.next();
        /* standard commands handled by factory */
        if (cmds.containsKey(cmd)) {
            op = cmds.get(cmd).create(scanner);
            img = op.execute(img);
        /* special cases for print and undo */
        } else if (cmd.equals(AsciiConstants.cmdPrint)) {
            if (scanner.hasNext()) {
                throw new AsciiException(AsciiConstants.errInp);
            }
            System.out.print(img.toString());
            System.out.println();
        } else if (cmd.equals(AsciiConstants.cmdUndo)) {
            if (scanner.hasNext()) {
                throw new AsciiException(AsciiConstants.errInp);
            }
            try {
                img = stack.pop();
            } catch (EmptyStackException ex) {
                System.out.println(AsciiConstants.errStackEmpty);
            }
        /* unknown cmd */
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
