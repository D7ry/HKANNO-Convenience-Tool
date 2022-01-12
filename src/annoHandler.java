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
        _name = txt.getName();
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
    public boolean add(boolean append, boolean once, String annoKwd, String newAnno, double timeDiff) {
        boolean added = false;
        ListIterator<String> iter = _lines.listIterator();
        while (iter.hasNext()) {
            String line = iter.next();
            if (compareAnno(line, annoKwd)) {
                if (append) {
                    String anno = (getAnnoTime(line) + timeDiff) + " " + newAnno;
                    iter.add(anno);
                    System.out.println("added " + anno + " after " + line);
                } else {
                    String anno = (getAnnoTime(line) - timeDiff) + " " + newAnno;
                    iter.previous();
                    iter.add(anno);
                    iter.next();
                    System.out.println("added " + anno + " before " + line);
                }
                added = true;
                if (once) {
                    return true;
                }
            }
        }
        return added;
    }

    /**default add with a minimum timeDiff. */
    public void add(boolean append, String annoKwd, String newAnno) {
        add(append, false, annoKwd, newAnno, 0.000001);
    }

    /**add annotation to the last presence of annotation with ANNOKWD. */
    public boolean addLast(boolean append, String annoKwd, String newAnno, double timeDiff) {
        int annoCt = 0;
        for (String line : _lines) {
            if (compareAnno(line, annoKwd)) {
                annoCt++;
            }
        }
        if (annoCt == 0) {
            return false; //short circuiting
        }
        ListIterator<String> iter = _lines.listIterator();
        while (iter.hasNext()) {
            String line = iter.next();
            if (compareAnno(line, annoKwd)) {
                if (annoCt == 1) {
                    if (append) {
                        String anno = (getAnnoTime(line) + timeDiff) + " " + newAnno;
                        iter.add(anno);
                        System.out.println("added " + anno + " after " + line);
                    } else {
                        String anno = (getAnnoTime(line) - timeDiff) + " " + newAnno;
                        iter.previous();
                        iter.add(anno);
                        System.out.println("added " + anno + " before " + line);
                    }
                    return true;
                } else {
                    annoCt --;
                }
            }
        }
        return false;
    }


    /**removes all annotation with keyword.
     * @param annoKwd keyword of annotation to be removed.
     */
    public boolean remove(String annoKwd) {
        boolean removed = false;
        ListIterator<String> iter = _lines.listIterator();
        while(iter.hasNext()) {
            String line = iter.next();
            if (compareAnno(line, annoKwd)) {
                iter.remove();
                System.out.println("removed " + line);
                removed = true;
            }
        }
        return removed;
    }

    /**replace all annotation with keyword into another annotation,
     * keeping time annotation. Case insensitive.
     */
    public boolean replace(String orgAnnoKwd, String newAnnoKwd) {
        boolean replaced = false;
        ListIterator<String> iter = _lines.listIterator();
        while(iter.hasNext()) {
            String line = iter.next();
            if (compareAnno(line, orgAnnoKwd)) {
                iter.remove();
                String time = line.split(" ")[0];
                iter.add(time + " " + newAnnoKwd);
                System.out.println("removed " + line);
                replaced = true;
            }
        }
        return replaced;
    }

    /**check iff annotation's string portion equals annoKwd.
     * case insensitive.*/
    private boolean compareAnno(String anno, String annokwd) {
        return anno.split(" " ).length > 1 && anno.split(" ")[1].toLowerCase(Locale.ROOT).equals(annokwd.toLowerCase(Locale.ROOT));
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

    /**name of the txt THIS handler manages. */
    private String _name;

    /**duration of the animation containing this annotation; can be null. */
    private String _duration;

    /**return duration of THIS anno. */
    public String getDuration() {
        return _duration;
    }

}
