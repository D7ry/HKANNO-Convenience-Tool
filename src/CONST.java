import java.io.File;

public class CONST {
    public static final File CWD = new File("").getAbsoluteFile();
    public static final File ANIM_DIR = new File(CWD, "animations");
    public static final File ANNO_DIR = new File(CWD, "annotations");
    public static final File HKANNO = new File(CWD, "hkanno64.exe");
    public static final String DUMP_CMD = "cmd /c " + HKANNO.toString() + " dump -o \"%s\" \"%s\"";
    public static final String UPDATE_CMD = "cmd /c " + HKANNO.toString() + " update -i \"%s\" \"%s\"";

    //folders for NPC combos
    public static final File COMBO_DIR = new File(CWD, "combos");
    public static final File TWO_L_ZERO_H = new File(COMBO_DIR, "2_light_0_hvy");
    public static final File TRI_L_ZERO_H = new File(COMBO_DIR, "3_light_0_hvy");
    public static final File ONE_L_ONE_H = new File(COMBO_DIR, "1_light_1_hvy");
    public static final File TWO_L_ONE_H = new File(COMBO_DIR, "2_light_1_hvy");

    public static final File[] NPC_COMBOS = new File[] {TWO_L_ONE_H, TWO_L_ZERO_H, ONE_L_ONE_H, TRI_L_ZERO_H};
}
