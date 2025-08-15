import java.util.Scanner;

public class Boof {
    public static void main(String[] args) {
        String userText;
        Task[] textStorage = new Task[100];
        int counter = 0;
        Scanner scanner = new Scanner(System.in);

        System.out.println(
                "Hello! I'm Boof\n" +
                        "What can I do for you?\n");

        while (true) {
            userText = scanner.nextLine();
            String[] parts = userText.split(" ");
            String command = parts[0];

            if (userText.equals("bye")) {
                break;

            } else if (userText.equals("list")) {
                System.out.println("    ------- Your List -------");
                for (int i = 0; i < counter; i++) {
                    System.out.printf("     %d.[%s] %s\n", i + 1, textStorage[i].getStatusIcon() ,textStorage[i].description);
                }
                System.out.println("    -------------------------");

            } else if (command.equals("mark")) {
                int taskIndex = Integer.parseInt(parts[1]) - 1;
                Task t = textStorage[taskIndex];
                
                t.markAsDone();
                System.out.println("    -------------------------");
                System.out.println("      Nice! I've marked this task as done:");
                System.out.printf("        [%s] %s\n", t.getStatusIcon(), t.description);
                System.out.println("    -------------------------");

            } else if (command.equals("unmark")) {
                int taskIndex = Integer.parseInt(parts[1]) - 1;
                Task t = textStorage[taskIndex];

                t.unmarkAsDone();
                System.out.println("    -------------------------");
                System.out.println("      OK, I've marked this task as not done yet:");
                System.out.printf("        [%s] %s\n", t.getStatusIcon(), t.description);
                System.out.println("    -------------------------");
            
            } else {
                System.out.println("    -------------------------");
                System.out.println("        added: " + userText);
                System.out.println("    -------------------------");
                textStorage[counter] = new Task(userText);
                counter++;
            }
        }
        System.out.println("    -------------------------");
        System.out.println("      Bye. Hope to see you again soon!");
        System.out.println("    -------------------------");
        scanner.close();

    }
}
