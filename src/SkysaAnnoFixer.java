import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class SkysaAnnoFixer {

    /**fix heavy attack annotations for skysa.
     * add loop annotations.
     */
    public static void fixHvy() throws FileNotFoundException {
        File[] txts = CONST.ANNO_DIR.listFiles();
        if (txts.length == 0) {
            System.out.println("empty annotation directory. Nothing to fix.");
            return;
        }
        Scanner input = new Scanner(System.in);
        System.out.println("input time difference between hitframe and attackLoop. Suggested time: 0.3 for 1h powers, 0.5 for 2h powers.");
        Double timeDiff = Double.parseDouble(input.next());
        for (File txt : txts) {
            String name = txt.getName();
            if (!name.contains("power" )) {
                System.out.println(name + " is not a power attack.");
                return;
            }
            annoHandler handler = new annoHandler(txt);
            if (handler.seek("SkySA_AttackLoop")) {
                System.out.println(name + " is already fixed.");
                continue;
            }
            System.out.println("fixing " + name);
            handler.addLast(false, "hitframe", "SkySA_TriggerIntervalWinLoop", 0.5);
            handler.addLast(true, "hitframe", "SkySA_AttackLoop", timeDiff);
            handler.remove("attackStop");
            handler.remove("SkySA_AttackWinEnd");
            handler.save();
            System.out.println("fixed " + name);
        }
        System.out.println("fix complete.");
    }

    /**add combo Annotations for NPC movesets.
     */
    public static void npcCombo(boolean lightAnno) throws FileNotFoundException {
        Scanner input = new Scanner(System.in);
        System.out.println("input time delay between attackWinStart and next attack. The shorter the delay, the quicker the enemy chains combo.");
        Double timeDiff = Double.parseDouble(input.next());
        String newAnnoStr = "";
        File[] txts = CONST.ANNO_DIR.listFiles();
        if (txts.length == 0) {
            System.out.println("empty annotation directory. Nothing to fix.");
            return;
        }
        if (lightAnno) {
            newAnnoStr = "attackStart";
        } else {
            newAnnoStr = "attackpowerstartforward";
        }
        for (File txt : txts) {
            String name = txt.getName();
            if (name.contains("power")) {
                System.out.println(name + " is an annotation file for power attack hkx, which cannot be fixed.");
                return;
            }
            annoHandler handler = new annoHandler(txt);
            if (handler.seek("attackStart") || handler.seek("attackpowerstartforward")) {
                System.out.println(name + "is already annotated for combos, use the original annotation or erase the combo annotation first.");
                return;
            }
            System.out.println("adding anno to " + name);
            if (handler.add(true, true, "SkySA_AttackWinStart", newAnnoStr, timeDiff)) {
                System.out.println("successfully added anno to " + name);
                handler.save();
            }
        }
    }
}
