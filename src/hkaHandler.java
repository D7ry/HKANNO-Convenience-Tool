import java.io.File;
import java.io.IOException;
import java.util.Locale;

/**handles all hkanno operations in CWD.
 * @author Ty */
public class hkaHandler {

    /**dump all hkx's annotatins in animation folder into annotation folder as txt,
     * text files are named after the original .hkx file.
     */
    public static void batchDump(File annoDir, File animDir) throws IOException, InterruptedException {
        File[] hkxs = animDir.listFiles();
        if (hkxs.length == 0) {
            System.out.println("no file present in animation folder!");
        }
        for (File hkx : hkxs) {
            dump(hkx, annoDir);
        }
        System.out.println("dump complete.");
    }

    /**update all text file's annotations in annotation folder back into animations in
     * animation folder.
     */
    public static void batchUpdate(File annoDir, File animDir) throws IOException, InterruptedException {
        File[] txts = annoDir.listFiles();
        if (txts.length == 0) {
            System.out.println("not file present in annotation folder!");
        }
        for (File txt : txts) {
            update(txt, animDir);
        }
        System.out.println("update complete.");
    }
    /**dumps a single hkx into corresponding text file in annotation folder.
     * @param hkx hkx file whose annotation will be dumped.
     * @param annoDir directory containing annotations.*/
    private static void dump(File hkx, File annoDir) throws IOException, InterruptedException {
        String hkxFullName = hkx.getName();
        if (!hkxFullName.toLowerCase(Locale.ROOT).contains(".hkx")) {
            //System.out.println(hkxFullName + " is not a valid animation file.");
            return;
        }
        String name = hkxFullName.split("\\.")[0];
        String txtName = name + ".txt";
        String txtPath = new File(annoDir, txtName).toString();
        String hkxPath = hkx.toString();
        String cmd = String.format(CONST.DUMP_CMD, txtPath, hkxPath);
        if (Runtime.getRuntime().exec(cmd).waitFor() == 0) {
            System.out.println("dumped " + hkxFullName + " to " + txtPath);
        }
    }

    /**update a single txt into corresponding hkx file in custom folder.
     * @param txt txt file containing annotation.
     * @param dir directory containing animations.*/
    private static void update(File txt, File dir) throws IOException, InterruptedException {
        String txtFullNAme = txt.getName();
        if (!txtFullNAme.toLowerCase(Locale.ROOT).contains(".txt")) {
            //System.out.println(txtFullNAme + " is not a valid annotation file.");
            return;
        }
        String name = txtFullNAme.split("\\.")[0];
        String hkxName = name + ".hkx";
        File hkx = new File(dir, hkxName);
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
