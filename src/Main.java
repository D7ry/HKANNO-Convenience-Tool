import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.FileSystems;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException, InterruptedException {
        checkDir();
        inputHandler();
    }



    /**check if directory is a valid directory and if HKANNO is included.
     * creates necessary directory if they don't exist.
     * throw error if HKANNO is not present.*/
    private static void checkDir() {
        System.out.println("current working directory: " + CONST.CWD);
        if (!CONST.ANIM_DIR.exists()) {
            System.out.println("animation directory not detected, creating new dir");
            CONST.ANIM_DIR.mkdir();
        }
        if (!CONST.ANNO_DIR.exists()) {
            System.out.println("annotation directory not detected, creating new dir");
            CONST.ANNO_DIR.mkdir();
        }
        if (CONST.CWD.toString().contains(" ") || CONST.CWD.toString().contains(".")) {
            hkctError.error("current directory path contains either space or '.', this will not work with HKANNO, " +
                    "please move the processor elsewhere or change the naming of current directory. ");
        }
        if (!CONST.HKANNO.exists()) {
            hkctError.error("Error: HKANNO not present in current directory, please download and extract it into the current directory.");
        }
    }

    /**handles user input and processes correspondingly. */
    private static void inputHandler() throws IOException, InterruptedException {
        Scanner input = new Scanner(System.in);
        while (true) {
            switch (input.next().toLowerCase(Locale.ROOT)) {
                case "dump": hkaHandler.batchDump(); break;
                case "update": hkaHandler.batchUpdate(); break;
            }
        }
    }


}

