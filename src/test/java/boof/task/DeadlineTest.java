package boof.task;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DeadlineTest {
	@Test
	public void toString_dateOnly() {
		Deadline d = new Deadline("return book", "2019-10-15");
		assertTrue(d.toString().contains("Oct 15 2019"));
	}

	@Test
	public void toString_dateTime() {
		Deadline d = new Deadline("return book", "2/12/2019 1800");
		assertTrue(d.toString().contains("Dec 02 2019 1800"));
	}

	@Test
	public void getByDateTime_parsesVariousFormats() {
		Deadline d1 = new Deadline("d1_test", "2025-01-01");
		assertEquals(LocalDateTime.of(2025, 1, 1, 0, 0), d1.getByDateTime());
		Deadline d2 = new Deadline("d2_test", "2025-02-02 1800");
		assertEquals(LocalDateTime.of(2025, 2, 2, 18, 0), d2.getByDateTime());
		Deadline d3 = new Deadline("d3_test", "2/12/2019 1800");
		assertEquals(LocalDateTime.of(2019, 12, 2, 18, 0), d3.getByDateTime());
	}

	@Test
	public void markAsDone_changesStatusIcon() {
		Deadline d = new Deadline("return book", "2019-10-15");
		assertEquals("[D][ ] return book (by: Oct 15 2019 0000)", d.toString());
		d.markAsDone();
		assertEquals("[D][X] return book (by: Oct 15 2019 0000)", d.toString());
	}
}
