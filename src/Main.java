import java.io.File;
import java.io.IOException;
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
        System.out.println("input commands. Input help to see all available commands.");
        Scanner input = new Scanner(System.in);
        while (true) {
            switch (input.next().toLowerCase(Locale.ROOT)) {
                case "d": hkaHandler.batchDump(CONST.ANNO_DIR, CONST.ANIM_DIR); break;
                case "u": hkaHandler.batchUpdate(CONST.ANNO_DIR, CONST.ANIM_DIR); break;
                case "add": annoBatchHandler.addHandler(); break;
                case "rm": annoBatchHandler.rmHandler(); break;
                case "rp": annoBatchHandler.rpHandler(); break;
                case "fixhvy": SkysaAnnoFixer.fixHvy(); break;
                case "help" : help(); break;
                case "clear" : clear(); break;
                case "npclight" : SkysaAnnoFixer.npcCombo(true, CONST.ANNO_DIR); break;
                case "npcheavy" : SkysaAnnoFixer.npcCombo(false, CONST.ANNO_DIR); break;
                case "combogen" : ComboGenerator.comboGen(); break;
                case "prehitfix" : annoBatchHandler.batchAdd("HitFrame", "preHitFrame", 0.1, false, true);
                default : System.out.println("please enter a valid command"); break;
            }
        }
    }

    /**prints out instructions. */
    private static void help() {
        System.out.println(
                "d : batch dump \n" +
                "u : batch update \n" +
                "add : batch add annotation\n" +
                "rm : batch remove annotation\n" +
                "rp : batch remove annotation\n" +
                "fixhvy : batch fix skysa heavy attack looping \n" +
                "clear : delete all files in annotation and animation folder \n" +
                "npclight : batch add light combo annotations \n" +
                "npcheavy : batch add heavy combo annotations \n" +
                "combogen : generate NPC combos into designated folders \n" +
                "prehitfix : add prehit frame annotations to annotations missing them \n");
    }

    private static void clear() {
        File[] hkxs = CONST.ANIM_DIR.listFiles();
        File[] txts = CONST.ANNO_DIR.listFiles();
        for (File hkx : hkxs) {
            hkx.delete();
        }
        for (File txt : txts) {
            txt.delete();
        }
        CONST.COMBO_DIR.delete();
        System.out.println("folder cleared.");
    }

}

