package boof.task;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Represents a deadline which extends from the task class.
 */
public class Deadline extends Task {
  private LocalDateTime byDateTime;
  private static final DateTimeFormatter INPUT_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd[ HHmm][ HH:mm]");
  private static final DateTimeFormatter OUTPUT_FORMAT = DateTimeFormatter.ofPattern("MMM dd yyyy HHmm");

  public Deadline(String description, String byDate) {
    super(description);
    this.byDateTime = parseDateTime(byDate);
  }

  /**
   * Returns the deadline date and time.
   * @return the deadline date and time
   */
  public LocalDateTime getByDateTime() {
    return byDateTime;
  }

  /**
   * Returns the deadline date as a formatted string.
   * @return the deadline date as a formatted string
   */
  public String getByDate() {
    return byDateTime.format(INPUT_FORMAT);
  }

  /**
   * Parses the input string into a LocalDateTime object.
   * @param input the input string to parse
   * @return the parsed LocalDateTime object
   */
  private static LocalDateTime parseDateTime(String input) {
    String[] patterns = {
      "yyyy-MM-dd HHmm", "yyyy-MM-dd HH:mm", "yyyy-MM-dd", "d/M/yyyy HHmm", "d/M/yyyy HH:mm", "d/M/yyyy"
    };

    for (String pattern : patterns) {
      try {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        if (pattern.contains("HH")) {
          return LocalDateTime.parse(input, formatter);
        } else {
          return LocalDateTime.parse(input + " 0000", DateTimeFormatter.ofPattern(pattern + " HHmm"));
        }
      } catch (DateTimeParseException e) {
      }
    }
    
    return LocalDateTime.now();
  }

  @Override
  public String toString() {
    return "[D][" + getStatusIcon() + "] " + description + " (by: " + byDateTime.format(OUTPUT_FORMAT) + ")";
  }
}