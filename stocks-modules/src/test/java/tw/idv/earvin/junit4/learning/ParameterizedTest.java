package tw.idv.earvin.junit4.learning;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(value=Parameterized.class)
public class ParameterizedTest {

	private double expected;
	private double valueOne; 
	private double valueTwo;
	
	@Parameters
	public static Collection<Integer[]> getTestParameters() {
		return Arrays.asList(new Integer[][] {
			{2, 1, 1},
			{3, 2, 1},
			{4, 3, 1},
		});
	}
	
	public ParameterizedTest(double expected, double valueOne, double valueTwo) {
		this.expected = expected;
		this.valueOne = valueOne;
		this.valueTwo = valueTwo;
	}
	
	@Test
	public void sum() {
		Calculator calc = new Calculator();
		// 這個函式已被捨棄，用了反而測試失敗!!
//		assertEquals(expected, calc.add(valueOne, valueTwo));
		assertEquals(expected, calc.add(valueOne, valueTwo), 0);
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
