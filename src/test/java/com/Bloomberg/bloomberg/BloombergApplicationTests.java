package com.Bloomberg.bloomberg;
import com.Bloomberg.bloomberg.api.ClientController;
import com.Bloomberg.bloomberg.service.ClientService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Basic unit tests on calculator
 *
 * @Author James McSkeane
 * @Version 1.0
 * @Date 25-11-2019
 */

@SpringBootTest
class BloombergApplicationTests {

	@Test
	void testClientController()
	{
		String result;
		//  Need to ensure Calculation Server is running before running unit tests
		ClientService cs = new ClientService();
		ClientController hc = new ClientController(cs);
		cs.setHost("127.0.0.2");
		cs.setPort(6565);

		//  Simple Add Test
		result = hc.getCalculation("+200/100");
		assert result.equals("300.0");

		//  Simple Subtract Test
		result = hc.getCalculation("-200/100");
		assert result.equals("100.0");

		//  Simple Divide Test
		result = hc.getCalculation(("/25/5"));
		assert result.equals("5.0");

		//  Simple Multiplication Test
		result = hc.getCalculation(("*5/5"));
		assert result.equals("25.0");

		//  Error checks
		result = hc.getCalculation(("*A/5"));
		assert result.equals("Cannot find calculation command { + * - / } Please use format +100/300");

		//  Error checks
		result = hc.getCalculation((""));
		assert result.equals("Cannot find calculation command { + * - / } Please use format +100/300");
	}
}
