package tw.idv.earvin.junit4.learning;

import static org.junit.Assert.*;
import org.junit.Test;

public class CalculatorTest {

	@Test
	public void testAdd() {
		Calculator calculator = new Calculator();
		double result = calculator.add(10, 50);
		assertEquals(60, result, 0);
	}

	
	
/** version 1.3寫法	
	private int nbErrors = 0;

	public void testAdd() {
		Calculator calculator = new Calculator();
		double result = calculator.add(10, 50);
		if (result != 60) {
			System.out.println("Bad result: " + result);
		}
	}

	public static void main(String[] args) {
		CalculatorTest test = new CalculatorTest();
		try {
			test.testAdd();
		} catch(Throwable e) {
			test.nbErrors++;
			e.printStackTrace();
		}
		
		if (test.nbErrors > 0) {
			throw new IllegalStateException("There were " + test.nbErrors + " error(s)");
		}
	}
**/	
}
