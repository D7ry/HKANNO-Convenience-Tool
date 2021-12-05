import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.ListIterator;
import java.util.Locale;
import java.util.Scanner;

/** class handling annos from a specific txt.
 * @author Ty
 */
public class annoHandler {

    /**a new anno handler handling TXT file. */
    public annoHandler(File txt) throws FileNotFoundException {
        if (!txt.getName().contains(".txt")) {
            System.out.println(txt.getName() + " is not a valid anno file");
        }
        _txt = txt;
        Scanner reader = new Scanner(txt);
        while (reader.hasNextLine()) {
            String line = reader.nextLine();
            if (line.contains("duration")) {
                _duration = line.split(" ")[2];
            }
            _lines.add(line);
        }
    }

    /**add specific annotation.
     * @param append adding new annotation before or after the annotation.
     * @param annoKwd keyword of annotation before/after which a new anno is added.
     * @param newAnno new annotation to be added without timestamp.
     * @param timeDiff absolute time difference between two annos.
     */
    public boolean add(boolean append, String annoKwd, String newAnno, double timeDiff) {
        boolean added = false;
        ListIterator<String> iter = _lines.listIterator();
        while (iter.hasNext()) {
            String line = iter.next();
            if (line.split(" ").length > 1
                    && line.split(" ")[1].toLowerCase(Locale.ROOT).equals(
                            annoKwd.toLowerCase(Locale.ROOT))) {
                if (append) {
                    String anno = (getAnnoTime(line) + timeDiff) + " " + newAnno;
                    iter.add(anno);
                    System.out.println("added " + anno + " after " + line);
                    added = true;
                } else {
                    String anno = (getAnnoTime(line) - timeDiff) + " " + newAnno;
                    iter.previous();
                    iter.add(anno);
                    iter.next();
                    System.out.println("added " + anno + " before " + line);
                    added = true;
                }
            }
        }
        return added;
    }

    /**default add with a minimum timeDiff. */
    public void add(boolean append, String annoKwd, String newAnno) {
        add(append, annoKwd, newAnno, 0.000001);
    }


    /**removes all annotation with keyword.
     * @param annoKwd keyword of annotation to be removed.
     */
    public boolean remove(String annoKwd) {
        boolean removed = false;
        ListIterator<String> iter = _lines.listIterator();
        while(iter.hasNext()) {
            String line = iter.next();
            if (line.contains(annoKwd)) {
                iter.remove();
                System.out.println("removed " + line);
                removed = true;
            }
        }
        return removed;
    }
    /**checks if anno with KWD exists. Return true if it does. */
    public boolean seek(String kwd) {
        for (String line : _lines) {
            if (line.contains(kwd)) {
                return true;
            }
        }
        return false;
    }

    /**write annotations in _lines back to the txt*/
    public void save() {
        PrintWriter pw = null;
        try {
            pw = new PrintWriter(_txt);
            for (String line : _lines) {
                pw.println(line);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (pw != null) {
                pw.close();
            }
        }
    }


    /**
     * return timestamp of the specific anno line
     */
    private double getAnnoTime(String line) {
        return Double.parseDouble(line.split(" ")[0]);
    }
    /**
     * return text of specific anno line that is not an AMR annotation.
     */
    private String getAnnoText(String line) {
        return line.split(" ")[1];
    }

    /**list of all lines in this txt representation of anno. */
    private ArrayList<String> _lines = new ArrayList<>();

    /**return lines in this anno as an arraylist. */
    public ArrayList<String> getAnnoLine() {
        return _lines;
    }

    /**txt THIS handler manages. */
    private File _txt;

    /**duration of the animation containing this annotation; can be null. */
    private String _duration;

    /**return duration of THIS anno. */
    public String getDuration() {
        return _duration;
    }

}
