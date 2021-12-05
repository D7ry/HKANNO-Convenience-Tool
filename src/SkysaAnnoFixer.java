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
        System.out.println("input time difference between hitframe and attackLoop. Suggested time: 0.2 for 1h powers, 0.3 for 2h powers.");
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
            handler.add(true, true, "hitframe", "SkySA_TriggerIntervalWinLoop", 0.000001);
            handler.add(true, true, "SkySA_TriggerIntervalWinLoop", "SkySA_AttackLoop", timeDiff);
            handler.remove("SkySA_AttackWinEnd");
            handler.save();
            System.out.println("fixed " + name);
        }
        System.out.println("fix complete.");
    }

}
