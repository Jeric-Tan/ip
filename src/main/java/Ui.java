import java.util.Scanner;

public class Ui {
  private final Scanner scanner;

  public Ui() {
    this.scanner = new Scanner(System.in);
  }

  public String readCommand() {
    return scanner.nextLine();
  }

  public void displayLine() {
    System.out.println("    -------------------------");
  }

  public void showWelcome() {
    System.out.println("Hello! I'm Boof\nWhat can I do for you?\n");
  }

  public void showExit() {
    displayLine();
    System.out.println("      Bye. Hope to see you again soon!");
    displayLine();
  }

  public void showError(String message) {
    displayLine();
    System.out.println("      OOPS!!! " + message);
    displayLine();
  }

  public void showLoadingError() {
    showError("Error loading tasks from file.");
  }

  public void showMessage(String message) {
    System.out.println(message);
  }
}
