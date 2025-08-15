import java.util.Scanner;

public class Boof {
    public static void main(String[] args) {
        String userText;
        Scanner scanner = new Scanner(System.in);

        System.out.println(
                "Hello! I'm Boof\n" +
                        "What can I do for you?\n");

        while (true) {
            userText = scanner.nextLine();
            System.out.println("    ---------------------------");
            System.out.println("    You said: " + userText);
            System.out.println("    ---------------------------");
            if (userText.equals("bye") || userText.equals("Bye")) {
                break;
            }
        }
        System.out.println("Bye. Hope to see you again soon!");
        scanner.close();

    }}
