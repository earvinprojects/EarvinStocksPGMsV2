package tw.idv.earvin.stockpgms.stocks_modules.tables;

import static org.junit.Assert.*;
import org.junit.Test;

public class StocksIndexesNameTest {
	@Test
	public void getIndexCodeTest() {
//		StocksIndexesName sin = new StocksIndexesName();
//		long result = sin.getIndexCode("MAP_9");
		long result = StocksIndexesName.getIndexCode("MAP_9");
		assertEquals(1009, result);
	}
}
