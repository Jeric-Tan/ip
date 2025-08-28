import java.io.*;
import java.util.ArrayList;

public class Storage {
    private final String filePath;

    public Storage(String filePath) {
        this.filePath = filePath;
    }

    public ArrayList<Task> load() {
        ArrayList<Task> tasks = new ArrayList<>();
        File file = new File(filePath);
        if (!file.exists()) {
            file.getParentFile().mkdirs();
            return tasks;
        }
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(" \\| ");
                String type = parts[0];
                boolean isDone = parts[1].equals("1");
                String description = parts[2];
                switch (type) {
                    case "T":
                        Task todo = new Todo(description);
                        if (isDone)
                            todo.markAsDone();
                        tasks.add(todo);
                        break;
                    case "D":
                        String by = parts[3];
                        Task deadline = new Deadline(description, by);
                        if (isDone)
                            deadline.markAsDone();
                        tasks.add(deadline);
                        break;
                    case "E":
                        String[] eventParts = parts[3].split(" ", 2);
                        String from = eventParts[0];
                        String to = eventParts.length > 1 ? eventParts[1] : "";
                        Task event = new Event(description, from, to);
                        if (isDone)
                            event.markAsDone();
                        tasks.add(event);
                        break;
                    default:
                        break;
                }

            }
        } catch (IOException e) {}
        return tasks;
    }

    public void save(ArrayList<Task> tasks) {
        File file = new File(filePath);
        file.getParentFile().mkdirs();
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
            for (Task task : tasks) {
                bw.write(taskToFileString(task));
                bw.newLine();
            }
        } catch (IOException e) {
        }
    }

    private String taskToFileString(Task task) {
        String type;
        String status = task.isDone ? "1" : "0";
        if (task instanceof Todo) {
            type = "T";
            return String.format("%s | %s | %s", type, status, task.description);
        } else if (task instanceof Deadline) {
            type = "D";
            return String.format("%s | %s | %s | %s", type, status, task.description, ((Deadline) task).getByDate());
        } else if (task instanceof Event) {
            type = "E";
            return String.format("%s | %s | %s | %s %s", type, status, task.description, ((Event) task).getFrom(),
                    ((Event) task).getTo());
        }
        return "";
    }
}
