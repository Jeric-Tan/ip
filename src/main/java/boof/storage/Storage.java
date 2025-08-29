package boof.storage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import boof.task.Deadline;
import boof.task.Event;
import boof.task.Task;
import boof.task.Todo;

/**
 * Handles the loading and saving tasks to a file.
 */
public class Storage {
    private final String filePath;

    public Storage(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Loads tasks from the storage file.
     * 
     * @return A list of tasks loaded from the file.
     */
    public ArrayList<Task> load() {
        ArrayList<Task> tasks = new ArrayList<>();
        File file = new File(filePath);
        if (!file.exists()) {
            file.getParentFile().mkdirs();
            return tasks;
        }
        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                if (!line.isEmpty()) {
                    String[] parts = line.split(" \\| ");
                    String type = parts[0];
                    boolean isDone = parts[1].equals("1");
                    String description = parts[2];
                    switch (type) {
                        case "T": {
                            Task todo = new Todo(description);
                            if (isDone)
                                todo.markAsDone();
                            tasks.add(todo);
                            break;
                        }
                        case "D": {
                            String by = parts[3];
                            Task deadline = new Deadline(description, by);
                            if (isDone)
                                deadline.markAsDone();
                            tasks.add(deadline);
                            break;
                        }
                        case "E": {
                            String[] eventParts = parts[3].split(" ", 2);
                            String from = eventParts[0];
                            String to = eventParts.length > 1 ? eventParts[1] : "";
                            Task event = new Event(description, from, to);
                            if (isDone)
                                event.markAsDone();
                            tasks.add(event);
                            break;
                        }
                        default:
                            break;
                    }
                }
            }
            scanner.close();
        } catch (IOException e) {
        }
        return tasks;
    }

    /**
     * Saves the given tasks to the storage file in data/.
     * @param tasks
     */
    public void save(ArrayList<Task> tasks) {
        File file = new File(filePath);
        file.getParentFile().mkdirs();
        try (FileWriter fw = new FileWriter(file)) {
            for (Task task : tasks) {
                fw.write(taskToFileString(task) + System.lineSeparator());
            }
        } catch (IOException e) {
        }
    }

    /**
     * Converts a task to its string representation for saving to file.
     * @param task the task to convert
     * @return formatted string or empty string if task is null
     */
    private String taskToFileString(Task task) {
        String type;
        String status = task.isDone() ? "1" : "0";
        if (task instanceof Todo) {
            type = "T";
            return String.format("%s | %s | %s", type, status, task.getDescription());
        } else if (task instanceof Deadline) {
            type = "D";
            Deadline d = (Deadline) task;
            String dateStr = d.getByDateTime().format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm"));
            return String.format("%s | %s | %s | %s", type, status, task.getDescription(), dateStr);
        } else if (task instanceof Event) {
            type = "E";
            return String.format("%s | %s | %s | %s %s", type, status, task.getDescription(), ((Event) task).getFrom(),
                    ((Event) task).getTo());
        }
        return "";
    }
}