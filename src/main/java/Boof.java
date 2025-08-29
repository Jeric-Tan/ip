import java.util.Scanner;
import java.util.ArrayList;

public class Boof {
    public static void main(String[] args) {
        String userText;
        Scanner scanner = new Scanner(System.in);
        Storage storage = new Storage("./data/boof.txt");
        ArrayList<Task> tasks = storage.load();

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
                for (int i = 0; i < tasks.size(); i++) {
                    System.out.printf("     %d.%s\n", i + 1, tasks.get(i).toString());
                }
                System.out.println("    -------------------------");

            } else if (command.equals("mark")) {
                int taskIndex = Integer.parseInt(parts[1]) - 1;
                Task t = tasks.get(taskIndex);
                t.markAsDone();
                storage.save(tasks);
                System.out.println("    -------------------------");
                System.out.println("      Nice! I've marked this task as done:");
                System.out.printf("        %s\n", t.toString());
                System.out.println("    -------------------------");

            } else if (command.equals("unmark")) {
                int taskIndex = Integer.parseInt(parts[1]) - 1;
                Task t = tasks.get(taskIndex);
                t.unmarkAsDone();
                storage.save(tasks);
                System.out.println("    -------------------------");
                System.out.println("      OK, I've marked this task as not done yet:");
                System.out.printf("        %s\n", t.toString());
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
                tasks.add(newTask);
                storage.save(tasks);
                System.out.println("    -------------------------");
                System.out.println("      Got it. I've added this task:");
                System.out.println("        " + newTask.toString());
                System.out.println("      Now you have " + tasks.size() + " tasks in the list.");
                System.out.println("    -------------------------");

            } else if (command.equals("deadline")) {
                if (parts.length < 2) {
                    System.out.println("    -------------------------");
                    System.out.println("      OOPS!!! The description of a deadline cannot be empty.");
                    System.out.println("    -------------------------");
                    continue;
                }

                String[] deadlineParts = userText.split(" /by ", 2);
                if (deadlineParts.length < 2) {
                    System.out.println("    -------------------------");
                    System.out.println(
                            "      OOPS!!! Please specify a /by date in the correct format (e.g., 2019-10-15 1800).");
                    System.out.println("    -------------------------");
                    continue;
                }
                String description = deadlineParts[0].substring(9);
                String byDate = deadlineParts[1];
                try {
                    Task newTask = new Deadline(description, byDate);
                    tasks.add(newTask);
                    storage.save(tasks);
                    System.out.println("    -------------------------");
                    System.out.println("      Got it. I've added this task:");
                    System.out.println("        " + newTask.toString());
                    System.out.println("      Now you have " + tasks.size() + " tasks in the list.");
                    System.out.println("    -------------------------");
                } catch (Exception e) {
                    System.out.println("    -------------------------");
                    System.out
                            .println("      OOPS!!! Invalid date format. Please use yyyy-MM-dd HHmm or d/M/yyyy HHmm.");
                    System.out.println("    -------------------------");
                }

            } else if (command.equals("event")) {
                if (parts.length < 2) {
                    System.out.println("    -------------------------");
                    System.out.println("      OOPS!!! The description of an event cannot be empty.");
                    System.out.println("    -------------------------");
                    continue;
                }

                String[] eventSplit = userText.split(" /from ");
                String description = eventSplit[0].substring(6);
                String[] fromTo = eventSplit[1].split(" /to ");
                String from = fromTo[0];
                String to = fromTo.length > 1 ? fromTo[1] : "";
                Task newTask = new Event(description, from, to);
                tasks.add(newTask);
                storage.save(tasks);
                System.out.println("    -------------------------");
                System.out.println("      Got it. I've added this task:");
                System.out.println("        " + newTask.toString());
                System.out.println("      Now you have " + tasks.size() + " tasks in the list.");
                System.out.println("    -------------------------");

            } else if (command.equals("delete")) {
                if (parts.length < 2) {
                    System.out.println("    -------------------------");
                    System.out.println("      OOPS!!! Specify the task number to delete.");
                    System.out.println("    -------------------------");
                    continue;
                }
                int taskIndex = Integer.parseInt(parts[1]) - 1;
                if (taskIndex < 0 || taskIndex >= tasks.size()) {
                    System.out.println("    -------------------------");
                    System.out.println("      OOPS!!! Task number does not exist.");
                    System.out.println("    -------------------------");
                    continue;
                }

                Task deletedTask = tasks.remove(taskIndex);
                storage.save(tasks);
                System.out.println("    -------------------------");
                System.out.println("      Noted. I've removed this task:");
                System.out.println("        " + deletedTask.toString());
                System.out.println("      Now you have " + tasks.size() + " tasks in the list.");
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
