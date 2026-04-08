package Utils;

public class ConsolePrinter {
    public static void clear() {
        try {
            String os = System.getProperty("os.name").toLowerCase();
            if (os.contains("win")) {
                new ProcessBuilder("cmd", "/c", "cls")
                    .inheritIO().start().waitFor();
            } else {
                new ProcessBuilder("clear")
                    .inheritIO().start().waitFor();
            }
        } catch (Exception e) {
            for (int i = 0; i < 50; i++) System.out.println();
        }
    }

    public static void printHeader(String title) {
        System.out.println("========================================");
        System.out.println("   " + title);
        System.out.println("========================================");
    }

    public static void printDivider() {
        System.out.println("----------------------------------------");
    }

    public static void printSuccess(String msg) {
        System.out.println("[SUCCESS] " + msg);
    }

    public static void printError(String msg) {
        System.out.println("[ERROR]   " + msg);
    }

    public static void printInfo(String msg) {
        System.out.println("[INFO]    " + msg);
    }

    public static void pause() {
        System.out.print("\nPress Enter to continue...");
        InputHelper.readLine();
    }
}