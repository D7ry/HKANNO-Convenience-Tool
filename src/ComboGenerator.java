import com.sun.scenario.effect.impl.state.HVSeparableKernel;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Locale;

import static java.nio.file.StandardCopyOption.*;

public class ComboGenerator {


    /**generate annotated Skysa combos for NPCs, putting them into separate folders
     */
    public static void comboGen() throws IOException, InterruptedException {
        initDir();
        copyHkx(CONST.ANIM_DIR, CONST.LIGHT_COMBO_DIR);
        hkaHandler.batchDump(CONST.LIGHT_COMBO_DIR, CONST.LIGHT_COMBO_DIR);
        SkysaAnnoFixer.npcCombo(true, CONST.LIGHT_COMBO_DIR);
        hkaHandler.batchUpdate(CONST.LIGHT_COMBO_DIR, CONST.LIGHT_COMBO_DIR);
        copyHkx(CONST.ANIM_DIR, CONST.HVY_COMBO_DIR);
        hkaHandler.batchDump(CONST.HVY_COMBO_DIR, CONST.HVY_COMBO_DIR);
        SkysaAnnoFixer.npcCombo(false, CONST.HVY_COMBO_DIR);
        hkaHandler.batchUpdate(CONST.HVY_COMBO_DIR, CONST.HVY_COMBO_DIR);
    }


    /**check iff necessary directory is setup, if not initialize them. */
    private static void initDir() {
        if (!CONST.COMBO_DIR.exists()) {
            CONST.COMBO_DIR.mkdir();
        }
        if (!CONST.LIGHT_COMBO_DIR.exists()) {
            CONST.LIGHT_COMBO_DIR.mkdir();
        }
        if (!CONST.HVY_COMBO_DIR.exists()) {
            CONST.HVY_COMBO_DIR.mkdir();
        }
    }

    /**copied hkxs from one folder to another
     * @param destDir folder hkxs go into.
     * @param orgDir folder storing hkxs to be copied.*/
    private static void copyHkx(File orgDir, File destDir) throws IOException {
        for (File hkx : orgDir.listFiles()) {
            if (hkx.getName().toLowerCase(Locale.ROOT).contains("hkx")) {
                Files.copy(orgDir.toPath(), destDir.toPath(), REPLACE_EXISTING);
            }
        }
    }
}
