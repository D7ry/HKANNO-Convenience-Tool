import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Locale;
import java.util.Scanner;
import static java.nio.file.StandardCopyOption.*;

public class ComboGenerator {

    /**generate annotated Skysa combos for NPCs, putting them into separate folders
     */
    public static void comboGen() throws IOException, InterruptedException {
        initDir();
        if (!checkHkx()) {
            hkctError.error("Error: animations in animations folder insufficient for NPC combo generation.");
        }
        two_l_zero_hvy_gen();
        two_l_one_hvy_gen();
        one_l_one_hvy_gen();
        three_l_zero_hvy_gen();
    }

    /**checks iff hkx present in animation dir is sufficient to generate full set of NPC combos.
     * @return iff hkxs presents are valid. */
    private static boolean checkHkx() {
        File[] hkxs = CONST.ANIM_DIR.listFiles();
        int lightAtkCt = 0;
        for (File hkx : hkxs) {
            String name = hkx.getName().toLowerCase(Locale.ROOT);
            if (!name.contains(".hkx")) {
                continue;
            }
            if (name.contains("skysa")) {
                if (name.contains("sword")) {
                    _wpnType = "skysa_sword";
                }
                if (name.contains("mace")) {
                    _wpnType = "skysa_mace";
                }
                if (name.contains("axe")) {
                    _wpnType = "skysa_axe";
                }
                if (name.contains("2hm")) {
                    _wpnType = "skysa_2hm";
                }
                if (name.contains("2hw")) {
                    _wpnType = "skysa_2hw";
                }
                lightAtkCt++;
            }
        }
        return (lightAtkCt >= 3);
    }

    /**check iff necessary directory is setup, if not initialize them. */
    private static void initDir() {
        if (!CONST.COMBO_DIR.exists()) {
            CONST.COMBO_DIR.mkdir();
        }
        for (File dir: CONST.NPC_COMBOS) {
            if (!dir.exists()) {
                dir.mkdir();
            }
        }
    }

    private static void two_l_zero_hvy_gen() throws IOException, InterruptedException {
        File light1hkx = new File(CONST.ANIM_DIR, _wpnType + "1.hkx" );
        File light2hkx = new File(CONST.ANIM_DIR, _wpnType + "2.hkx" );
        File cLight1hkx = new File(CONST.TWO_L_ZERO_H, _wpnType + "1.hkx" );
        File cLight2hkx = new File(CONST.TWO_L_ZERO_H, _wpnType + "2.hkx" );
        Files.copy(light1hkx.toPath(), cLight1hkx.toPath()); Files.copy(light2hkx.toPath(), cLight2hkx.toPath());
        hkaHandler.batchDump(CONST.TWO_L_ZERO_H, CONST.TWO_L_ZERO_H);
        File clight1txt = new File(CONST.TWO_L_ZERO_H, _wpnType + "1.txt");
        SkysaAnnoFixer.annoNPChkx(clight1txt, "attackStart");
        hkaHandler.batchUpdate(CONST.TWO_L_ZERO_H, CONST.TWO_L_ZERO_H);
    }

    private static void two_l_one_hvy_gen() throws IOException, InterruptedException {
        File light1hkx = new File(CONST.ANIM_DIR, _wpnType + "1.hkx" );
        File light2hkx = new File(CONST.ANIM_DIR, _wpnType + "2.hkx" );
        File cLight1hkx = new File(CONST.TWO_L_ONE_H, _wpnType + "1.hkx" );
        File cLight2hkx = new File(CONST.TWO_L_ONE_H, _wpnType + "2.hkx" );
        Files.copy(light1hkx.toPath(), cLight1hkx.toPath()); Files.copy(light2hkx.toPath(), cLight2hkx.toPath());
        hkaHandler.batchDump(CONST.TWO_L_ONE_H, CONST.TWO_L_ONE_H);
        File clight1txt = new File(CONST.TWO_L_ONE_H, _wpnType + "1.txt");
        File clight2txt = new File(CONST.TWO_L_ONE_H, _wpnType + "2.txt");
        SkysaAnnoFixer.annoNPChkx(clight1txt, "attackStart");
        SkysaAnnoFixer.annoNPChkx(clight2txt, "attackpowerstartforward");
        hkaHandler.batchUpdate(CONST.TWO_L_ONE_H, CONST.TWO_L_ONE_H);
    }

    private static void one_l_one_hvy_gen() throws IOException, InterruptedException {
        File light1hkx = new File(CONST.ANIM_DIR, _wpnType + "1.hkx" );
        File cLight1hkx = new File(CONST.ONE_L_ONE_H, _wpnType + "1.hkx" );
        Files.copy(light1hkx.toPath(), cLight1hkx.toPath());
        hkaHandler.batchDump(CONST.ONE_L_ONE_H, CONST.ONE_L_ONE_H);
        File clight1txt = new File(CONST.ONE_L_ONE_H, _wpnType + "1.txt");
        SkysaAnnoFixer.annoNPChkx(clight1txt, "attackpowerstartforward");
        hkaHandler.batchUpdate(CONST.ONE_L_ONE_H, CONST.ONE_L_ONE_H);
    }
    private static void three_l_zero_hvy_gen() throws IOException, InterruptedException {
        File light1hkx = new File(CONST.ANIM_DIR, _wpnType + "1.hkx" );
        File light2hkx = new File(CONST.ANIM_DIR, _wpnType + "2.hkx" );
        File light3hkx = new File(CONST.ANIM_DIR, _wpnType + "3.hkx" );
        File cLight1hkx = new File(CONST.TRI_L_ZERO_H, _wpnType + "1.hkx" );
        File cLight2hkx = new File(CONST.TRI_L_ZERO_H, _wpnType + "2.hkx" );
        File cLight3hkx = new File(CONST.TRI_L_ZERO_H, _wpnType + "3.hkx" );
        Files.copy(light1hkx.toPath(), cLight1hkx.toPath()); Files.copy(light2hkx.toPath(), cLight2hkx.toPath()); Files.copy(light3hkx.toPath(), cLight3hkx.toPath());
        hkaHandler.batchDump(CONST.TRI_L_ZERO_H, CONST.TRI_L_ZERO_H);
        File clight1txt = new File(CONST.TRI_L_ZERO_H, _wpnType + "1.txt");
        File clight2txt = new File(CONST.TRI_L_ZERO_H, _wpnType + "2.txt");
        File clighr3txt = new File(CONST.TRI_L_ZERO_H, _wpnType + "3.txt");
        SkysaAnnoFixer.annoNPChkx(clight1txt, "attackStart");
        SkysaAnnoFixer.annoNPChkx(clight2txt, "attackStart");
        hkaHandler.batchUpdate(CONST.TRI_L_ZERO_H, CONST.TRI_L_ZERO_H);
    }

    /**forwards hkxs in animations folder into new combo folder.
     * @param dir folder to store new combo hkxs.*/
    private static void copyHkx(File dir) throws IOException {
        for (File hkx : CONST.ANIM_DIR.listFiles()) {
            if (hkx.getName().toLowerCase(Locale.ROOT).contains(".hkx")) {
                File comboHkx = new File(dir, hkx.getName());
                Files.copy(hkx.toPath(), comboHkx.toPath(), REPLACE_EXISTING);
            }
        }
    }

    private static String _wpnType;


}
