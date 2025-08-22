public class Deadline extends Task {
  private String byDate;

  public Deadline(String description, String byDate) {
    super(description);
    this.byDate = byDate;
  }

  @Override
  public String toString() {
    return "[D][" + getStatusIcon() + "] " + description + " (by: " + byDate + ")";
  }
}