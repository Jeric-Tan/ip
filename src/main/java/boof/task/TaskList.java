package boof.task;

import java.util.ArrayList;

/**
 * Represents a list of tasks.
 */
public class TaskList {
    private ArrayList<Task> tasks;

    /**
     * Constructor which creates a new empty TaskList.
     */
    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    /**
     * Constructor which creates a new TaskList with the specified tasks.
     *
     * @param tasks the tasks to include in the list
     */
    public TaskList(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    /**
     * Adds a task to the list.
     *
     * @param task the task to add
     */
    public void addTask(Task task) {
        tasks.add(task);
    }

    /**
     * Removes a task from the list.
     *
     * @param index the index of the task to remove
     * @return the removed task
     */
    public Task removeTask(int index) {
        return tasks.remove(index);
    }

    /**
     * Returns a task from the list.
     *
     * @param index the index of the task to return
     * @return the task at the specified index
     */
    public Task getTask(int index) {
        return tasks.get(index);
    }

    /**
     * Returns the number of tasks in the list.
     *
     * @return the number of tasks in the list
     */
    public int getTaskListSize() {
        return tasks.size();
    }

    /**
     * Returns all tasks in the list.
     *
     * @return all tasks in the list
     */
    public ArrayList<Task> getAllTasks() {
        return tasks;
    }
}
