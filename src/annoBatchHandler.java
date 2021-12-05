import java.io.File;
import java.io.FileNotFoundException;
import java.util.Locale;
import java.util.Scanner;

/**class to handle annotation txts in batch */
public class annoBatchHandler {

    /**add annotation to txt. */
    public static void addHandler() throws FileNotFoundException {
        Scanner input = new Scanner(System.in);
        System.out.println("input keyword for annotation next to which to add annotation");
        String kwd = input.next();
        System.out.println("input annotation name to be added");
        String anno = input.next();
        System.out.println("input time difference");
        Double time = Double.parseDouble(input.next());
        System.out.println("append anno? [Y/N]");
        switch (input.next().toLowerCase(Locale.ROOT)) {
            case "y" : batchAdd(kwd, anno, time, true, false); break;
            case "n" : batchAdd(kwd, anno, time, false, false); break;
        }
        System.out.println("add complete.");
    }

    public static void batchAdd(String annoKwd, String newAnno, double time, boolean append, boolean once) throws FileNotFoundException {
        File[] txts = CONST.ANNO_DIR.listFiles();
        for (File txt : txts) {
            annoHandler handler = new annoHandler(txt);
            if (handler.add(append, once, annoKwd, newAnno, time)) {
                handler.save();
            } else {
                System.out.println("not matching keyword found in " + txt.getName());
            }
        }
    }

    /**remove annotation from txt. */
    public static void rmHandler() throws FileNotFoundException {
        Scanner input = new Scanner(System.in);
        System.out.println("input keyword for the annotation to be removed.");
        batchRemove(input.next());
        System.out.println("remove complete.");
    }

    public static void batchRemove(String annoKwd) throws FileNotFoundException {
        File[] txts = CONST.ANNO_DIR.listFiles();
        for (File txt: txts) {
            System.out.println("processing " + txt.getName());
            annoHandler handler = new annoHandler(txt);
            if (handler.remove(annoKwd)) {
                handler.save();
            } else {
                System.out.println("not annotation with matching keyword found in " + txt.getName());
            }
        }
    }

}
