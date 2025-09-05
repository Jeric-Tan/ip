package boof.task;

/**
 * Represents an event which extends from the task class.
 */
public class Event extends Task {
    private String from;
    private String to;

    /**
     * Constructor which creates a new event.
     *
     * @param description the description of the event
     * @param from        the starting time of the event
     * @param to          the ending time of the event
     */
    public Event(String description, String from, String to) {
        super(description);
        this.from = from;
        this.to = to;
    }

    /**
     * Returns the starting time of the event.
     *
     * @return the starting time of the event
     */
    public String getFrom() {
        return from;
    }

    /**
     * Returns the ending time of the event.
     *
     * @return the ending time of the event
     */
    public String getTo() {
        return to;
    }

    @Override
    public String toString() {
        return "[E][" + getStatusIcon() + "] " + description
                + " (from: " + from + " to: " + to + ")";
    }
}
