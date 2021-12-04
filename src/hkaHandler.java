import java.io.File;
import java.io.IOException;
import java.util.Locale;

/**handles hkanno in CWD. */
public class hkaHandler {

    /**dump all hkx's annotatins in animation folder into annotation folder as txt,
     * text files are named after the original .hkx file.
     */
    public static void batchDump() throws IOException, InterruptedException {
        File[] hkxs = CONST.ANIM_DIR.listFiles();
        if (hkxs.length == 0) {
            System.out.println("no file present in animation folder!");
        }
        for (File hkx : hkxs) {
            dump(hkx);
        }
    }

    /**update all text file's annotations in annotation folder back into animations in
     * animation folder.
     */
    public static void batchUpdate() throws IOException, InterruptedException {
        File[] txts = CONST.ANNO_DIR.listFiles();
        if (txts.length == 0) {
            System.out.println("not file present in annotation folder!");
        }
        for (File txt : CONST.ANNO_DIR.listFiles()) {
            update(txt);
        }
    }
    /**dumps a single hkx into corresponding text file in annotation folder. */
    private static void dump(File hkx) throws IOException, InterruptedException {
        String hkxFullName = hkx.getName();
        if (!hkxFullName.toLowerCase(Locale.ROOT).contains(".hkx")) {
            System.out.println(hkxFullName + " is not a valid animation file.");
            return;
        }
        String name = hkxFullName.split("\\.")[0];
        String txtName = name + ".txt";
        String txtPath = new File(CONST.ANNO_DIR, txtName).toString();
        String hkxPath = hkx.toString();
        String cmd = String.format(CONST.DUMP_CMD, txtPath, hkxPath);
        if (Runtime.getRuntime().exec(cmd).waitFor() == 0) {
            System.out.println("dumped " + hkxFullName + " to " + txtPath);
        }
    }

    /**updates a single txt into corresponding hkx file in animation folder. */
    private static void update(File txt) throws IOException, InterruptedException {
        String txtFullNAme = txt.getName();
        if (!txtFullNAme.toLowerCase(Locale.ROOT).contains(".txt")) {
            System.out.println(txtFullNAme + " is not a valid annotation file.");
            return;
        }
        String name = txtFullNAme.split("\\.")[0];
        String hkxName = name + ".hkx";
        File hkx = new File(CONST.ANIM_DIR, hkxName);
        if (!hkx.exists()) {
            System.out.println("failed to update " + txtFullNAme + ". " + hkxName + " doesn't exist in animation folder.");
            return;
        }
        String hkxPath = hkx.toString();
        String txtPath = txt.toString();
        String cmd = String.format(CONST.UPDATE_CMD, txtPath, hkxPath);
        if (Runtime.getRuntime().exec(cmd).waitFor() == 0) {
            System.out.println("updated " + txtFullNAme + " to " + hkxPath);
        }
    }
    /**return a list of HKX present in animation folder. */
    private static File[] getHkxList() {
        return CONST.ANIM_DIR.listFiles();
    }
    /**return a list of text files present in annotation folder. */
    private static File[] getAnnoList() {
        return CONST.ANNO_DIR.listFiles();
    }
}
