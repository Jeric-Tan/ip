import java.util.Scanner;

public class Boof {
    public static void main(String[] args) {
        String userText;
        String[] textStorage = new String[100];
        int counter = 0;
        Scanner scanner = new Scanner(System.in);

        System.out.println(
                "Hello! I'm Boof\n" +
                        "What can I do for you?\n");

        while (true) {
            userText = scanner.nextLine();

            if (userText.equals("bye")) {
                break;
            } else if (userText.equals("list")) {
                System.out.println("   -------- Your List ---------");
                for (int i = 0; i < counter; i++) {
                    System.out.printf("     %d. %s\n", i + 1, textStorage[i]);
                }
                System.out.println("    -------------------------");
            } else {
                System.out.println("    -------------------------");
                System.out.println("added: " + userText);
                System.out.println("    -------------------------");
                textStorage[counter] = userText;
                counter++;
            }
        }

        System.out.println("Bye. Hope to see you again soon!");
        scanner.close();

    }
}
