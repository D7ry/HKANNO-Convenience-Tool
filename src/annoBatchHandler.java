import java.io.File;
import java.io.FileNotFoundException;
import java.util.Locale;
import java.util.Scanner;

/**class to handle annotation txts in batch */
public class annoBatchHandler {

    public static void add() throws FileNotFoundException {
        Scanner input = new Scanner(System.in);
        System.out.println("input keyword for annotation next to which to add annotation");
        String kwd = input.next();
        System.out.println("input annotation name to be added");
        String anno = input.next();
        System.out.println("input time difference");
        Double time = Double.parseDouble(input.next());
        System.out.println("append anno? [Y/N]");
        switch (input.next().toLowerCase(Locale.ROOT)) {
            case "y" : batchAdd(kwd, anno, time, true);
            case "n" : batchAdd(kwd, anno, time, false);
        }
    }

    public static void rm() throws FileNotFoundException {
        Scanner input = new Scanner(System.in);
        System.out.println("input keyword for the annotation to be removed.");
        batchRemove(input.next());
    }

    public static void batchAdd(String annoKwd, String newAnno, double time, boolean append) throws FileNotFoundException {
        File[] txts = CONST.ANNO_DIR.listFiles();
        for (File txt : txts) {
            annoHandler handler = new annoHandler(txt);
            handler.add(append, annoKwd, newAnno, time);
            handler.save();
        }
    }

    public static void batchRemove(String annoKwd) throws FileNotFoundException {
        File[] txts = CONST.ANNO_DIR.listFiles();
        for (File txt: txts) {
            annoHandler handler = new annoHandler(txt);
            handler.remove(annoKwd);
            handler.save();
        }
    }

}
