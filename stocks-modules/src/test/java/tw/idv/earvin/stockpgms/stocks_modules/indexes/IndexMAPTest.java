package tw.idv.earvin.stockpgms.stocks_modules.indexes;

import static org.junit.Assert.*;
import org.junit.Test;

import tw.idv.earvin.junit4.learning.Calculator;

public class IndexMAPTest {

	public void getStocksDataTest() {
		IndexMAP indexMap = new IndexMAP();

	}
	@Test
	public void testAdd() {
		IndexMAP indexMap = new IndexMAP();
		StocksData[] sd = calculateMAP("2349");
		double result = calculator.add(10, 50);
		assertEquals(60, result, 0);
	}
}
