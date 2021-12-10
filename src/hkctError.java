import java.util.Scanner;

public class hkctError {

    /**when program errors, keep the program open and prints error message.
     * @param msg error message to print.
     */
    public static void error(String msg) {
        System.out.println(msg);
        Scanner scanner = new Scanner(System.in);
        scanner.next();
        System.exit(0);
    }
}
