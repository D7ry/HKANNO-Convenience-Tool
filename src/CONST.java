import java.io.File;

public class CONST {
    public static final File CWD = new File("").getAbsoluteFile();
    public static final File ANIM_DIR = new File(CWD, "animations");
    public static final File ANNO_DIR = new File(CWD, "annotations");
    public static final File HKANNO = new File(CWD, "hkanno64.exe");
    public static final String DUMP_CMD = "cmd /c " + HKANNO.toString() + " dump -o \"%s\" \"%s\"";
    public static final String UPDATE_CMD = "cmd /c " + HKANNO.toString() + " update -i \"%s\" \"%s\"";
}
