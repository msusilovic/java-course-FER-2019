package hr.fer.zemris.java.hw01;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class FactorialTest {

	@Test
	public void NumberOk() {

		long result = Factorial.factorial(5);
		assertEquals(result, 120);
	}

	@Test
	public void numberTooLarge() {
		assertThrows(Exception.class, () -> Factorial.factorial(23));
	}

	@Test
	public void numberTooSmall() {
		assertThrows(Exception.class, () -> Factorial.factorial(-3));
	}
	
}
