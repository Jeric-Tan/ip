package boof.task;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TodoTest {
    @Test
    public void todoToString_notDone() {
        Todo todo = new Todo("read book");
        assertEquals("[T][ ] read book", todo.toString());
    }

    @Test
    public void todoToString_done() {
        Todo todo = new Todo("read book");
        todo.markAsDone();
        assertEquals("[T][X] read book", todo.toString());
    }

    @Test
    public void getStatusIcon_notDone() {
        Todo todo = new Todo("read book");
        assertEquals(" ", todo.getStatusIcon());
    }

    @Test
    public void getStatusIcon_done() {
        Todo todo = new Todo("read book");
        todo.markAsDone();
        assertEquals("X", todo.getStatusIcon());
    }
}
