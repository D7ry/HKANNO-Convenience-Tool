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
        System.out.println("input time difference between hitframe and attackLoop. Suggested time: 0.2");
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
            handler.add(true, "hitframe", "SkySA_TriggerIntervalWinLoop");
            handler.add(true, "SkySA_TriggerIntervalWinLoop", "SkySA_AttackLoop", timeDiff);
            handler.save();
            System.out.println("fixed " + name);
        }
    }

}
