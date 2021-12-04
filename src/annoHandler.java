import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
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
    private void add(boolean append, String annoKwd, String newAnno, float timeDiff) {
        for (int i = 0; i < _lines.size(); i++) {
            String line = _lines.get(i);
            if (line.contains(annoKwd)) {
                if (append) {
                    newAnno += " " + (Float.parseFloat(getAnnoTime(line)) + timeDiff);
                    _lines.add(i + 1, newAnno);
                } else {
                    newAnno += " " + (Float.parseFloat(getAnnoTime(line)) - timeDiff);
                    _lines.add(i, newAnno)
                }
            }
        }
    }


    /**removes all annotation with keyword.
     * @param annoKwd keyword of annotation to be removed.
     */
    private void remove(String annoKwd) {
        for () //FIXME
    }

    /**write annotations in _lines back to the txt*/
    private void save() {
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
    private String getAnnoTime(String line) {
        return line.split(" ")[0];
    }
    /**
     * return text of specific anno line that is not an AMR annotation.
     */
    private String getAnnoText(String line) {
        return line.split(" ")[1];
    }

    /**list of all lines in this txt representation of anno. */
    private ArrayList<String> _lines;

    /**txt THIS handler manages. */
    private File _txt;

    /**duration of the animation containing this annotation; can be null. */
    private String _duration;

}
