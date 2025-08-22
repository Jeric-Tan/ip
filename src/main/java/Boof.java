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
                    System.out.printf("     %d.%s\n", i + 1, textStorage[i].toString());
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
            
            } else if (command.equals("todo")) {
                if (parts.length < 2) {
                    System.out.println("    -------------------------");
                    System.out.println("      OOPS!!! The description of a todo cannot be empty.");
                    System.out.println("    -------------------------");
                    continue;
                }
                String description = userText.substring(5);
                Task newTask = new Todo(description);
                textStorage[counter] = newTask;
                counter++;
                
                System.out.println("    -------------------------");
                System.out.println("      Got it. I've added this task:");
                System.out.println("        " + newTask.toString());
                System.out.println("      Now you have " + counter + " tasks in the list.");
                System.out.println("    -------------------------");

            } else if (command.equals("deadline")) {
                if (parts.length < 2) {
                    System.out.println("    -------------------------");
                    System.out.println("      OOPS!!! The description of a deadline cannot be empty.");
                    System.out.println("    -------------------------");
                    continue;
                }

                String[] deadlineParts = userText.split(" /by ");
                String description = deadlineParts[0].substring(9);
                String byDate = deadlineParts[1];
                Task newTask = new Deadline(description, byDate);
                textStorage[counter] = newTask;
                counter++;
                
                System.out.println("    -------------------------");
                System.out.println("      Got it. I've added this task:");
                System.out.println("        " + newTask.toString());
                System.out.println("      Now you have " + counter + " tasks in the list.");
                System.out.println("    -------------------------");

            } else if (command.equals("event")) {
                if (parts.length < 2) {
                    System.out.println("    -------------------------");
                    System.out.println("      OOPS!!! The description of an event cannot be empty.");
                    System.out.println("    -------------------------");
                    continue;
                }

                String[] eventParts = userText.split(" /from | /to ");
                String description = eventParts[0].substring(6);
                String from = eventParts[1];
                String to = eventParts[2];
                Task newTask = new Event(description, from, to);
                textStorage[counter] = newTask;
                counter++;

                System.out.println("    -------------------------");
                System.out.println("      Got it. I've added this task:");
                System.out.println("        " + newTask.toString());
                System.out.println("      Now you have " + counter + " tasks in the list.");
                System.out.println("    -------------------------");

            } else if (command.equals("delete")) {
                if (parts.length < 2) {
                    System.out.println("    -------------------------");
                    System.out.println("      OOPS!!! Specify the task number to delete.");
                    System.out.println("    -------------------------");
                    continue;
                }
                int taskIndex = Integer.parseInt(parts[1]) - 1;
                if (taskIndex < 0 || taskIndex >= counter) {
                    System.out.println("    -------------------------");
                    System.out.println("      OOPS!!! Task number does not exist.");
                    System.out.println("    -------------------------");
                    continue;
                }

                Task deletedTask = textStorage[taskIndex];
                for (int i = taskIndex; i < counter - 1; i++) {
                    textStorage[i] = textStorage[i + 1];
                }
                textStorage[counter - 1] = null; 
                counter--;

                System.out.println("    -------------------------");
                System.out.println("      Noted. I've removed this task:");
                System.out.println("        " + deletedTask.toString());
                System.out.println("      Now you have " + counter + " tasks in the list.");
                System.out.println("    -------------------------");


            } else {
                System.out.println("    -------------------------");
                System.out.println("        Idk what that means! Use either todo deadline or event to create a task!");
                System.out.println("    -------------------------");
            }
        }
        System.out.println("    -------------------------");
        System.out.println("      Bye. Hope to see you again soon!");
        System.out.println("    -------------------------");
        scanner.close();

    }
}
