package boof.task;
import java.util.ArrayList;

/**
 * Represents a list of tasks.
 */
public class TaskList {
  private ArrayList<Task> tasks;

  /**
   * Creates a new Empty TaskList.
   */
  public TaskList() {
    this.tasks = new ArrayList<>();
  }

  /**
   * Creates a new TaskList with the specified tasks.
   * @param tasks the tasks to include in the list
   */
  public TaskList(ArrayList<Task> tasks) {
    this.tasks = tasks;
  }

  /**
   * Adds a task to the list.
   * @param task the task to add
   */
  public void add(Task task) {
    tasks.add(task);
  }

  /**
   * Removes a task from the list.
   * @param index the index of the task to remove
   * @return the removed task
   */
  public Task remove(int index) {
    return tasks.remove(index);
  }

  /**
   * Returns a task from the list.
   * @param index the index of the task to return
   * @return the task at the specified index
   */
  public Task get(int index) {
    return tasks.get(index);
  }

  /**
   * Returns the number of tasks in the list.
   * @return the number of tasks in the list
   */
  public int size() {
    return tasks.size();
  }

  /**
   * Returns all tasks in the list.
   * @return all tasks in the list
   */
  public ArrayList<Task> getAll() {
    return tasks;
  }
}
